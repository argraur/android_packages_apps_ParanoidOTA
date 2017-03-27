/*
 * Copyright 2014 ParanoidAndroid Project
 *
 * This file is part of Paranoid OTA.
 *
 * Paranoid OTA is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Paranoid OTA is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Paranoid OTA.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.pixelrom.ota.helpers.recovery;

import android.content.Context;

import com.pixelrom.ota.IOUtils;
import com.pixelrom.ota.Utils;

import java.util.ArrayList;
import java.util.List;

public class AospRecovery extends RecoveryInfo {

    public AospRecovery() {
        super();

        setId(Utils.AOSP);
        setName("aosp");
        setInternalSdcard("data/media/0");
        setExternalSdcard("sdcard");
    }

    @Override
    public String getCommandsFile() {
        return "command";
    }

    @Override
    public String[] getCommands(Context context, String[] items, String[] originalItems,
            boolean wipeData, boolean wipeCaches, String backupOptions)
            throws Exception {

        List<String> commands = new ArrayList<String>();

        int size = items.length, i = 0;

        boolean hasAndroidSecure = IOUtils.hasAndroidSecure();
        boolean hasSdExt = IOUtils.hasSdExt();
        
        commands.add("--set_encrypted_filesystem=on");
        if (wipeData) {
            commands.add("--wipe_data");
        }
        if (wipeCaches) {
            commands.add("--wipe_cache");
        }

        for (; i < size; i++) {
            commands.add("--update_package=" + items[i]);
        }

        return commands.toArray(new String[commands.size()]);

    }
}
