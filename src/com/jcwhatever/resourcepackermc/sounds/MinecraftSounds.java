/*
 * This file is part of ResourcePackerMC for Bukkit, licensed under the MIT License (MIT).
 *
 * Copyright (c) JCThePants (www.jcwhatever.com)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.jcwhatever.resourcepackermc.sounds;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * A static utility use to hold default minecraft sounds.
 */
public class MinecraftSounds {

    private MinecraftSounds() {}

    private static Set<String> _paths = new HashSet<>(100);

    /**
     * Determine if a sound path matches a default Minecraft sound.
     *
     * @param soundPath  The sound path to check.
     */
    public static boolean contains(String soundPath) {

        if (soundPath.endsWith(".ogg")) {
            soundPath = soundPath.substring(0, soundPath.length() - 4);
        }

        //remove trailing number
        for (int i = soundPath.length() - 1; i >= 0; i--) {
            char ch = soundPath.charAt(i);

            if (!Character.isDigit(ch)) {
                soundPath = soundPath.substring(0, i + 1);
                break;
            }
        }

        return _paths.contains(soundPath);
    }

    /**
     * Remove all sounds from a collection that are default minecraft sounds.
     *
     * @param sounds  The sound collection to modify.
     *
     * @return The collection passed into the sounds parameter.
     */
    public static Collection<OggSound> removeMinecraft(Collection<OggSound> sounds) {

        // remove standard minecraft files from list
        Iterator<OggSound> iterator = sounds.iterator();

        while (iterator.hasNext()) {
            OggSound sound = iterator.next();

            String soundPath = sound.getSoundPath();
            if (soundPath == null) {
                iterator.remove();
                continue;
            }

            if (MinecraftSounds.contains(soundPath))
                iterator.remove();
        }

        return sounds;
    }

    static {
        add("ambient/cave/cave");
        add("ambient/weather/rain");
        add("ambient/weather/thunder");
        add("damage/fallbig");
        add("damage/fallsmall");
        add("damage/hit");
        add("dig/cloth");
        add("dig/grass");
        add("dig/gravel");
        add("dig/sand");
        add("dig/snow");
        add("dig/stone");
        add("dig/wood");
        add("fire/fire");
        add("fire/ignite");
        add("fireworks/blast_far");
        add("fireworks/blast1");
        add("fireworks/largeBlast_far");
        add("fireworks/largeBlast");
        add("fireworks/launch");
        add("fireworks/twinkle_far");
        add("fireworks/twinkle");
        add("liquid/lava");
        add("liquid/lavapop");
        add("liquid/splash");
        add("liquid/splash2");
        add("liquid/swim");
        add("liquid/water");
        add("minecart/base");
        add("minecart/inside");
        add("mob/bat/death");
        add("mob/bat/hurt");
        add("mob/bat/idle");
        add("mob/bat/loop");
        add("mob/bat/takeoff");

        add("mob/blaze/breathe");
        add("mob/blaze/death");
        add("mob/blaze/hit");

        add("mob/cat/hiss");
        add("mob/cat/hit");
        add("mob/cat/meow");
        add("mob/cat/purr");
        add("mob/cat/purreow");

        add("mob/chicken/hurt");
        add("mob/chicken/plop");
        add("mob/chicken/say");
        add("mob/chicken/step");

        add("mob/cow/hurt");
        add("mob/cow/say");
        add("mob/cow/step");

        add("mob/creeper/death");
        add("mob/creeper/say");

        add("mob/enderdragon/");
        add("mob/enderdragon/growl");
        add("mob/enderdragon/hit");
        add("mob/enderdragon/wings");

        add("mob/endermen/death");
        add("mob/endermen/hit");
        add("mob/endermen/idle");
        add("mob/endermen/portal");
        add("mob/endermen/scream");
        add("mob/endermen/stare");

        add("mob/ghast/affectionate_scream");
        add("mob/ghast/affectionate scream");
        add("mob/ghast/charge");
        add("mob/ghast/death");
        add("mob/ghast/fireball");
        add("mob/ghast/moan");
        add("mob/ghast/scream");

        add("mob/horse/angry");
        add("mob/horse/armor");
        add("mob/horse/breathe");
        add("mob/horse/death");
        add("mob/horse/gallop");
        add("mob/horse/hit");
        add("mob/horse/idle");
        add("mob/horse/jump");
        add("mob/horse/land");
        add("mob/horse/leather");
        add("mob/horse/soft");
        add("mob/horse/wood");

        add("mob/horse/donkey/angry");
        add("mob/horse/donkey/death");
        add("mob/horse/donkey/hit");
        add("mob/horse/donkey/idle");

        add("mob/horse/skeleton/death");
        add("mob/horse/skeleton/hit");
        add("mob/horse/skeleton/idle");

        add("mob/horse/zombie/death");
        add("mob/horse/zombie/hit");
        add("mob/horse/zombie/idle");

        add("mob/irongolem/death");
        add("mob/irongolem/hit");
        add("mob/irongolem/throw");
        add("mob/irongolem/walk");

        add("mob/magmacube/big");
        add("mob/magmacube/jump");
        add("mob/magmacube/small");

        add("mob/pig/death");
        add("mob/pig/say");
        add("mob/pig/step");

        add("mob/sheep/say");
        add("mob/sheep/step");
        add("mob/sheep/shear");

        add("mob/silverfish/hit");
        add("mob/silverfish/kill");
        add("mob/silverfish/say");
        add("mob/silverfish/step");

        add("mob/skeleton/death");
        add("mob/skeleton/hurt");
        add("mob/skeleton/say");
        add("mob/skeleton/step");

        add("mob/slime/attack");
        add("mob/slime/big");
        add("mob/slime/small");

        add("mob/spider/death");
        add("mob/spider/say");
        add("mob/spider/step");

        add("mob/villager/death");
        add("mob/villager/haggle");
        add("mob/villager/hit");
        add("mob/villager/idle");
        add("mob/villager/no");
        add("mob/villager/yes");

        add("mob/wither/death");
        add("mob/wither/hurt");
        add("mob/wither/idle");
        add("mob/wither/shoot");
        add("mob/wither/spawn");

        add("mob/wolf/bark");
        add("mob/wolf/death");
        add("mob/wolf/growl");
        add("mob/wolf/howl");
        add("mob/wolf/hurt");
        add("mob/wolf/panting");
        add("mob/wolf/shake");
        add("mob/wolf/step");
        add("mob/wolf/whine");

        add("mob/zombie/death");
        add("mob/zombie/hurt");
        add("mob/zombie/infect");
        add("mob/zombie/metal");
        add("mob/zombie/remedy");
        add("mob/zombie/say");
        add("mob/zombie/step");
        add("mob/zombie/unfect");
        add("mob/zombie/wood");
        add("mob/zombie/woodbreak");

        add("mob/zombiepig/zpig");
        add("mob/zombiepig/zpigangry");
        add("mob/zombiepig/zpigdeath");
        add("mob/zombiepig/zpighurt");

        add("note/bass");
        add("note/bassattack");
        add("note/bd");
        add("note/harp");
        add("note/hat");
        add("note/pling");
        add("note/snare");

        add("portal/portal");
        add("portal/travel");
        add("portal/trigger");

        add("random/anvil_break");
        add("random/anvil_land");
        add("random/anvil_use");
        add("random/bow");
        add("random/bowhit");
        add("random/break");
        add("random/breath");
        add("random/burp");
        add("random/chestclosed");
        add("random/chestopen");
        add("random/classic_hurt");
        add("random/click");
        add("random/door_close");
        add("random/door_open");
        add("random/drink");
        add("random/eat");
        add("random/explode");
        add("random/fizz");
        add("random/fuse");
        add("random/glass");
        add("random/levelup");
        add("random/orb");
        add("random/pop");
        add("random/splash");
        add("random/successful_hit");
        add("random/wood_click");

        add("step/cloth");
        add("step/grass");
        add("step/gravel");
        add("step/ladder");
        add("step/sand");
        add("step/snow");
        add("step/stone");
        add("step/wood");

        add("tile/piston/in");
        add("tile/piston/out");

        add("music/game/creative/creative");
        add("music/game/end/boss");
        add("music/game/end/credits");
        add("music/game/end/end");

        add("music/game/nether/nether");

        add("music/game/calm");
        add("music/game/hal");
        add("music/game/nuance");
        add("music/game/piano");

        add("music/menu/menu");

        add("records/11");
        add("records/13");
        add("records/blocks");
        add("records/cat");
        add("records/chirp");
        add("records/far");
        add("records/mall");
        add("records/mellohi");
        add("records/stal");
        add("records/strad");
        add("records/wait");
        add("records/ward");
    }

    private static void add(String sound) {
        _paths.add(sound);
    }
}
