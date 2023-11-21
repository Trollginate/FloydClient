package net.wurstclient.mixin;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.random.Random;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public abstract class ChangeHeartsMixin
{
    private final Random random = Random.create();
    private static final Identifier AIR_TEXTURE = new Identifier("hud/air");
    private static final Identifier AIR_BURSTING_TEXTURE = new Identifier("hud/air_bursting");
    @Inject(at = @At("RETURN"),
            method = "renderHealthBar(Lnet/minecraft/client/gui/DrawContext;Lnet/minecraft/entity/player/PlayerEntity;IIIIFIIIZ)V")
    private void renderHealthBar(DrawContext context, PlayerEntity player, int x, int y, int lines, int regeneratingHeartIndex, float maxHealth, int lastHealth, int health, int absorption, boolean blinking, CallbackInfo ci) {

        boolean bl = player.getWorld().getLevelProperties().isHardcore();
        int i = MathHelper.ceil((double)maxHealth / 2.0);
        int j = MathHelper.ceil((double)absorption / 2.0);
        int k = i * 2;
        for (int l = i + j - 1; l >= 0; --l) {
            boolean bl4;
            int r;
            boolean bl2;
            int m = l / 10;
            int n = l % 10;
            int o = x + n * 8;
            int p = y - m * lines;
            if (lastHealth + absorption <= 4) {
                p += this.random.nextInt(2);
            }
            if (l < i && l == regeneratingHeartIndex) {
                p -= 2;
            }
            this.drawHeart(context, o, p, bl, blinking, false);
            int q = l * 2;
            boolean bl3 = bl2 = l >= i;
            if (bl2 && (r = q - k) < absorption) {
                boolean bl32 = r + 1 == absorption;
                this.drawHeart(context, o, p, bl, false, bl32);
            }
            if (blinking && q < health) {
                bl4 = q + 1 == health;
                this.drawHeart(context, o, p, bl, true, bl4);
            }
            if (q >= lastHealth) continue;
            bl4 = q + 1 == lastHealth;
            this.drawHeart(context, o, p, bl, false, bl4);
        }
    }

    private void drawHeart(DrawContext context, int x, int y, boolean hardcore, boolean blinking, boolean half) {
        context.drawGuiTexture(getTexture(hardcore, half, blinking), x, y, 9, 9);
    }

    private Identifier getTexture(boolean hardcore, boolean half, boolean blinking) {
        if (half) return this.AIR_BURSTING_TEXTURE;
        return this.AIR_TEXTURE;
    }
}
