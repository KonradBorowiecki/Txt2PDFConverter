package utility;

import java.io.*;
import java.nio.channels.*;

public class FileOps {
	/** Required to make sure it is not instantiated. */
	private FileOps() {
	}

	public static void copyFile(File in, File out) throws IOException {
		FileChannel inChannel = new FileInputStream(in).getChannel();
		FileChannel outChannel = new FileOutputStream(out).getChannel();
		try {
			// magic number for Windows, 64Mb - 32Kb)
			int maxCount = (64 * 1024 * 1024) - (32 * 1024);
			long size = inChannel.size();
			long position = 0;
			while(position < size)
				position += inChannel.transferTo(position, maxCount, outChannel);
		}catch(IOException e) {
			throw e;
		}finally {
			if(inChannel != null)
				inChannel.close();
			if(outChannel != null)
				outChannel.close();
		}
	}

	/** copies a directory and all contained files and directories that it contains */
	public static void copyDirectory(File srcPath, File dstPath) throws IOException {
		if(srcPath.isDirectory()) {
			if(!dstPath.exists())
				dstPath.mkdir();
			String files[] = srcPath.list();

			for(int i = 0; i < files.length; i++)
				copyDirectory(new File(srcPath, files[i]),
						new File(dstPath, files[i]));
			System.out.println("Directory " + srcPath.getName() + " and its content was copied.");
		}
		else {
			if(!srcPath.exists()) {
				System.out.println("File or directory does not exist.");
				System.exit(0);
			}
			else
				copyFile(srcPath, dstPath);
		}
	}

	/*Gets the extension of a given file.*/
	public static String getExtension(File f) {
		String ext = null;
		String s = f.getName();
		int i = s.lastIndexOf('.');

		if(i > 0 && i < s.length() - 1)
			ext = s.substring(i + 1).toLowerCase();
		return ext;
	}

	public static void main(String args[]) throws IOException {
		FileOps.copyFile(new File(args[0]), new File(args[1]));
	}
}
