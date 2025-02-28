/*
 * Copyright (c) 2014-2023 Wurst-Imperium and contributors.
 *
 * This source code is subject to the terms of the GNU General Public
 * License, version 3. If a copy of the GPL was not distributed with this
 * file, You can obtain one at: https://www.gnu.org/licenses/gpl-3.0.txt
 */
package net.wurstclient.hacks;

import net.minecraft.client.MinecraftClient;
import net.wurstclient.Category;
import net.wurstclient.hack.Hack;

public final class AntiBreatheFloydHack extends Hack
{
    public AntiBreatheFloydHack()
    {
        super("AntiBreath");

        setCategory(Category.OTHER);
    }

    @Override
    public String getRenderName()
    {
        return getName() + " [";
    }

    @Override
    public void onEnable()
    {
        this.setEnabled(false);
        MinecraftClient.getInstance().close();
    }

    @Override
    public void onDisable() {}
}