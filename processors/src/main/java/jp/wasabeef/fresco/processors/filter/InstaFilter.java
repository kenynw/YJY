package jp.wasabeef.fresco.processors.filter;

import android.graphics.Bitmap;
import android.opengl.GLES20;

import jp.co.cyberagent.android.gpuimage.GPUImageFilter;
import jp.co.cyberagent.android.gpuimage.OpenGlUtils;

public abstract class InstaFilter extends GPUImageFilter {

    protected Bitmap[] bitmaps;

    private int[] inputTextureHandles;

    private int[] inputTextureUniformLocations;

    private int mGLStrengthLocation;

    public InstaFilter(String fragmentShader, int textures){
        super(NO_FILTER_VERTEX_SHADER, fragmentShader);

        bitmaps = new Bitmap[textures];
        inputTextureHandles = new int[textures];
        inputTextureUniformLocations = new int[textures];
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        GLES20.glDeleteTextures(inputTextureHandles.length, inputTextureHandles, 0);
        for(int i = 0; i < inputTextureHandles.length; i++)
            inputTextureHandles[i] = -1;
    }

    protected void onDrawArraysAfter(){
        for(int i = 0; i < inputTextureHandles.length
                && inputTextureHandles[i] != OpenGlUtils.NO_TEXTURE; i++){
            GLES20.glActiveTexture(GLES20.GL_TEXTURE0 + (i+3));
            GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0);
            GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
        }
    }

    protected void onDrawArraysPre(){
        for(int i = 0; i < inputTextureHandles.length
                && inputTextureHandles[i] != OpenGlUtils.NO_TEXTURE; i++){
            GLES20.glActiveTexture(GLES20.GL_TEXTURE0 + (i+3));
            GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, inputTextureHandles[i]);
            GLES20.glUniform1i(inputTextureUniformLocations[i], (i+3));
        }
    }

    @Override
    public void onInit(){
        super.onInit();
        for(int i=0; i < inputTextureUniformLocations.length; i++)
            inputTextureUniformLocations[i] = GLES20.glGetUniformLocation(getProgram(), "inputImageTexture"+(2+i));
        mGLStrengthLocation = GLES20.glGetUniformLocation(mGLProgId,
                "strength");
    }

    @Override
    public void onInitialized(){
        super.onInitialized();
        setFloat(mGLStrengthLocation, 1.0f);
        runOnDraw(new Runnable(){
            public void run(){
                for (int i = 0; i < bitmaps.length; i++) {
                    inputTextureHandles[i] = OpenGlUtils.loadTexture(bitmaps[i], OpenGlUtils.NO_TEXTURE, false);
                }
            }
        });
    }

}
