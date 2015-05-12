package gui;

import java.io.File;

import javax.swing.filechooser.FileFilter;

public class OrderFileFilter extends FileFilter {

	@Override
	public boolean accept(File file) {
		
		if(file.isDirectory()){
			return true;
		}
		
		String name = file.getName();
		
		String extension = Utils.getFileException(name);
		
		if(extension == null){
			return false;
		}
		
		if(extension.equals("ord")){
			return true;
		}
		
		return false;
	}

	@Override
	public String getDescription() {
		return "Order Database files (*.ord)";
	}

}
