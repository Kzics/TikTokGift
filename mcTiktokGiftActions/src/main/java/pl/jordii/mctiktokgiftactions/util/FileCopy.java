// 
// Decompiled by Procyon v0.5.36
// 

package pl.jordii.mctiktokgiftactions.util;

import java.io.OutputStream;
import java.io.InputStream;
import java.io.IOException;
import java.io.FileOutputStream;
import pl.jordii.mctiktokgiftactions.McTiktokGiftActions;
import java.io.File;

public class FileCopy
{
    private FileCopy() {
        throw new AssertionError();
    }
    
    public static void createFileFromResource(final String resourceName, final File file, final McTiktokGiftActions plugin) {
        if (!file.exists()) {
            try {
                final InputStream input = plugin.getResource(resourceName);
                try {
                    final OutputStream output = new FileOutputStream(file);
                    try {
                        file.createNewFile();
                        final byte[] buffer = new byte[1024];
                        int length;
                        while ((length = input.read(buffer)) > 0) {
                            output.write(buffer, 0, length);
                        }
                        output.close();
                    }
                    catch (Throwable t) {
                        try {
                            output.close();
                        }
                        catch (Throwable exception) {
                            t.addSuppressed(exception);
                        }
                        throw t;
                    }
                    if (input != null) {
                        input.close();
                    }
                }
                catch (Throwable t2) {
                    if (input != null) {
                        try {
                            input.close();
                        }
                        catch (Throwable exception2) {
                            t2.addSuppressed(exception2);
                        }
                    }
                    throw t2;
                }
            }
            catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
