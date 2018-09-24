/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.recognition.software.jdeskew;
import java.io.File;
import net.sourceforge.tess4j.*;

public class ReadImage {

	public static void main(String[] args){	
		
		File imageFile = new File("tessdata/401076-binary.png");
		Tesseract instance = Tesseract.getInstance();
		instance.setLanguage("eng");
		
		try {
			String result = instance.doOCR(imageFile);
			System.out.println(result);
		} catch (TesseractException e) {
			System.err.println(e.getMessage());
		}
	}
}