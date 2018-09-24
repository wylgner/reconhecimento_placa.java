/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.recognition.software.jdeskew;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Binarizacao {

    public static void main(String[] args) {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileFilter(new FileNameExtensionFilter("Image files", "png",
                "jpg", "bmp"));
        chooser.setAcceptAllFileFilterUsed(false);

        if (chooser.showOpenDialog(null) != JFileChooser.APPROVE_OPTION)
            return;

        try {
            // Carregamos a imagem do disco
            File img = chooser.getSelectedFile();
            BufferedImage image = ImageIO.read(img);

            // Geramos uma versão em escala de cinza
            BufferedImage gray = toGrayscale(image);
            ImageIO.write(gray, "png", newFile(img, "gray"));

            // Geramos uma versão binária a partir da versão em escala de cinza.
            BufferedImage binary = toBinary(gray, 128);
            ImageIO.write(binary, "png", newFile(img, "binary"));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao processar imagem.");
            e.printStackTrace();
        }
    }

    /**
     * Converte a imagem para escala de cinza.
     * 
     * @param image Imagem a ser convertida
     * @return a Imagem convertida.
     */
    private static BufferedImage toGrayscale(BufferedImage image)
            throws IOException {
        BufferedImage output = new BufferedImage(image.getWidth(),
                image.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
        Graphics2D g2d = output.createGraphics();
        g2d.drawImage(image, 0, 0, null);
        g2d.dispose();
        return output;
    }

    /**
     * Converte a imagem para binário usando um algorítmo de threshold simples.
     * Tudo que estiver abaixo do threshold será convertido para preto. Acima ou
     * igual ao threshold será convertido para branco.
     * 
     * @param image
     *            Imagem a ser convertida (apenas o canal r será considerado).
     *            Use uma escala já transformada em escala de cinza para
     *            melhores resultados.
     * @param t
     *            Valor do threshold.
     */
    private static BufferedImage toBinary(BufferedImage image, int t) {
        int BLACK = Color.BLACK.getRGB();
        int WHITE = Color.WHITE.getRGB();

        BufferedImage output = new BufferedImage(image.getWidth(),
                image.getHeight(), BufferedImage.TYPE_BYTE_GRAY);

        // Percorre a imagem definindo na saída o pixel como branco se o valor
        // na entrada for menor que o threshold, ou como preto se for maior.
        for (int y = 0; y < image.getHeight(); y++)
            for (int x = 0; x < image.getWidth(); x++) {
                Color pixel = new Color(image.getRGB(x, y));
                output.setRGB(x, y, pixel.getRed() < t ? BLACK : WHITE);
            }

        return output;
    }

    /**
     * Gera um nome de arquivo baseado no nome fornecido. O arquivo terá o mesmo
     * caminho do nome original.
     * <p>
     * Exemplo: Se o arquivo for c:\fish.png e a string de detalhe for "gray" a
     * saída será c:\fish-gray.png
     * 
     * A extensão final sempre será png.
     * 
     * @param file Arquivo original
     * @param detail String de detalhe
     * @return O nome do arquivo.
     */
    private static File newFile(File file, String detail) {
        return new File(file.getParentFile(), file.getName().substring(0,
                file.getName().indexOf("."))
                + "-" + detail + ".png");

    }

}