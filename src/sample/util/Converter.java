package sample.util;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Converter {
    public static Boolean isDate(String value) {
        return value.matches("(0[1-9]|[12][0-9]|3[01])[- /.](0[1-9]|1[012])[- /.](19|20)\\d\\d");
    }

    public static Date convertStringToDate(String value) {
        DateFormat format = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());

        try {
            return format.parse(value);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static java.sql.Date convertStringToSqlDate(String value) {
        DateFormat format = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());

        try {
            return new java.sql.Date(format.parse(value).getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static byte[] getBytesArrayFromImage(String pathImage) {
        File imgPath = new File(pathImage);

        BufferedImage bufferedImage;
        try {
            bufferedImage = ImageIO.read(imgPath);
        } catch (IOException e) {
            e.printStackTrace();

            return null;
        }

        WritableRaster raster = bufferedImage.getRaster();
        DataBufferByte data = (DataBufferByte) raster.getDataBuffer();

        return (data.getData());
    }

    public static Image getImageFromBytesArray(byte[] bytes) {
        Image img = new Image(new ByteArrayInputStream(bytes));
        GraphicsContext graphicsContext = new Canvas().getGraphicsContext2D();
        graphicsContext.drawImage(img, 0,0, 450, 500);

        return graphicsContext.getCanvas().snapshot(null, null);
    }

    public static String getFormattedSqlDate(String sqlDate) {
        SimpleDateFormat oldDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        SimpleDateFormat newDateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());

        Date date = null;
        try {
            date = oldDateFormat.parse(sqlDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return newDateFormat.format(date);
    }

    public static String getCurrentDate() {
        Date dateNow = new Date();

        SimpleDateFormat formatForDateNow = new SimpleDateFormat("dd.MM.yyyy");

        return formatForDateNow.format(dateNow);
    }
}
