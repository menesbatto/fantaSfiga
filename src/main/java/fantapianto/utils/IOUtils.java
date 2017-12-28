package fantapianto.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public class IOUtils {

	
	public static <T> void write (String filePathName, T object){
	
		ObjectOutputStream oos;
		try {
			oos = new ObjectOutputStream(new FileOutputStream(filePathName));
			oos.writeObject(object);
			oos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static <T> T read(String filename,  Class<T> clazz) {

		T address = null;

		FileInputStream fin = null;
		ObjectInputStream ois = null;

		try {
			File f = new File(filename);
			if (!f.exists())
				return null;
			fin = new FileInputStream(filename);
			ois = new ObjectInputStream(fin);
			address = (T) ois.readObject();

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {

			if (fin != null) {
				try {
					fin.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			if (ois != null) {
				try {
					ois.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}

		return address;

	}
	
	public static <T> ArrayList<T> read(String filename,  ArrayList<Class<T>> clazz) {

		ArrayList<T> address = null;

		FileInputStream fin = null;
		ObjectInputStream ois = null;

		try {

			fin = new FileInputStream(filename);
			ois = new ObjectInputStream(fin);
			address = (ArrayList<T>) ois.readObject();

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {

			if (fin != null) {
				try {
					fin.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			if (ois != null) {
				try {
					ois.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}

		return address;

	}
	
}
