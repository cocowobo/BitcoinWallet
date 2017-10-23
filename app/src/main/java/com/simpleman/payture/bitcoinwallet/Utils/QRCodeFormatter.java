package com.simpleman.payture.bitcoinwallet.Utils;

import android.graphics.Bitmap;
import android.graphics.Color;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;

public class QRCodeFormatter {

    public static Bitmap encodeTextToBitmap(String text, int size){
        BitMatrix bitMatrix;

        try{
            bitMatrix = new MultiFormatWriter().encode(
                    text,
                    BarcodeFormat.DATA_MATRIX.QR_CODE,
                    size, size, null
            );
        } catch ( Exception ex ){
            return null;
        }

        int bitMatrixWidth = bitMatrix.getWidth();

        int bitMatrixHeight = bitMatrix.getHeight();

        int[] pixels = new int[bitMatrixWidth * bitMatrixHeight];

        for (int y = 0; y < bitMatrixHeight; y++) {
            int offset = y * bitMatrixWidth;
            for (int x = 0; x < bitMatrixWidth; x++) {
                pixels[offset + x] = bitMatrix.get(x, y) ? Color.BLACK : Color.WHITE;
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(bitMatrixWidth, bitMatrixHeight, Bitmap.Config.ARGB_4444);
        bitmap.setPixels(pixels, 0, size, 0, 0, bitMatrixWidth, bitMatrixHeight);
        return bitmap;
    }

}
