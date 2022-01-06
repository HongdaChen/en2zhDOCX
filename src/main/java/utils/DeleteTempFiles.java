package utils;

import java.io.File;

public class DeleteTempFiles {
    //删除目录下所有file
    public static void main(File path) {
        File[] files = path.listFiles();
        for( File f : files){
            f.delete();
        }
    }
}
