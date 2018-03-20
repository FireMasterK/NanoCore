package me.idarkyy.nanocore.utils;

import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class InventoryUtils {
    public InventoryUtils() {
    }

    public static String itemStackArrayToBase64(ItemStack[] paramArrayOfItemStack) {
        try {
            ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
            BukkitObjectOutputStream localBukkitObjectOutputStream = new BukkitObjectOutputStream(localByteArrayOutputStream);

            localBukkitObjectOutputStream.writeInt(paramArrayOfItemStack.length);

            for (int i = 0; i < paramArrayOfItemStack.length; i++) {
                localBukkitObjectOutputStream.writeObject(paramArrayOfItemStack[i]);
            }

            localBukkitObjectOutputStream.close();
            return Base64Coder.encodeLines(localByteArrayOutputStream.toByteArray());
        } catch (Exception e) {
            throw new IllegalStateException("Unable to save item stacks.", e);
        }
    }

    public static ItemStack[] itemStackArrayFromBase64(String paramString) {
        try {
            ByteArrayInputStream localByteArrayInputStream = new ByteArrayInputStream(Base64Coder.decodeLines(paramString));
            BukkitObjectInputStream localBukkitObjectInputStream = new BukkitObjectInputStream(localByteArrayInputStream);
            ItemStack[] arrayOfItemStack = new ItemStack[localBukkitObjectInputStream.readInt()];

            for (int i = 0; i < arrayOfItemStack.length; i++) {
                arrayOfItemStack[i] = ((ItemStack) localBukkitObjectInputStream.readObject());
            }

            localBukkitObjectInputStream.close();
            return arrayOfItemStack;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
