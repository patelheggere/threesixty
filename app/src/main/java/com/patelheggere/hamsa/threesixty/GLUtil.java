package com.patelheggere.hamsa.threesixty;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.opengl.GLUtils;
import android.util.Log;
import java.io.IOException;
import java.io.InputStream;

/**
 * Utility class for working with GL.
 */
public class GLUtil {

    private static final String TAG = "GLUtil";

    /**
     * Checks GL state for errors and logs a message then throw a RuntimeExecption when one is
     * encountered. Should be called regularly after calls to GL functions to help with debugging.
     *
     * @param tag The tag to use when logging.
     * @param op The name of the GL function called before calling checkGlError
     */
    public static void checkGlError(String tag, String op) {
        int lastError = GLES20.GL_NO_ERROR;
        int error;
        while ((error = GLES20.glGetError()) != GLES20.GL_NO_ERROR) {
            Log.e(tag, op + ": glError " + error);
            lastError = error;
        }
        if (lastError != GLES20.GL_NO_ERROR) {
            throw new RuntimeException(op + ": glError " + lastError);
        }
    }

    /**
     * Creates a texture from an Android resource.
     *
     * @param context An Application context.
     * @param textureId The GL texture ID to the resource should be bound to.
     * @param resourceId The app resource ID to be bound to the texture.
     */
    public static void createResourceTexture(Context context, int textureId, int resourceId) {
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureId);

        GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_NEAREST);
        GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);

        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_REPEAT);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_REPEAT);

        InputStream is = context.getResources().openRawResource(resourceId);
        Bitmap bitmap;
        try {
            bitmap = BitmapFactory.decodeStream(is);
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                // Ignore.
            }
        }

        GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, bitmap, 0);
        bitmap.recycle();
    }

    /**
     * Creates a shader from a source string.
     *
     * @param shaderType The type of shader to create, either GL_FRAGMENT_SHADER or GL_VERTEX_SHADER.
     * @param source The source of the shader to load.
     * @return A GL object of the created shader if successful, 0 otherwise.
     */
    private static int loadShader(int shaderType, String source) {
        int shader = GLES20.glCreateShader(shaderType);
        if (shader != 0) {
            GLES20.glShaderSource(shader, source);
            GLES20.glCompileShader(shader);
            int[] compiled = new int[1];
            GLES20.glGetShaderiv(shader, GLES20.GL_COMPILE_STATUS, compiled, 0);
            if (compiled[0] == 0) {
                Log.e(TAG, "Could not compile shader " + shaderType + ":");
                Log.e(TAG, GLES20.glGetShaderInfoLog(shader));
                GLES20.glDeleteShader(shader);
                shader = 0;
            }
        }
        return shader;
    }

    /**
     * Creates a GL program from an Android resource.
     *
     * @param vertexSource The source of the vertex shader.
     * @param fragmentSource The source of the fragment shader.
     * @return A GL object of the created program if successful, 0 otherwise.
     */
    public static int createProgram(String vertexSource, String fragmentSource) {
        int vertexShader = loadShader(GLES20.GL_VERTEX_SHADER, vertexSource);
        if (vertexShader == 0) {
            return 0;
        }
        int pixelShader = loadShader(GLES20.GL_FRAGMENT_SHADER, fragmentSource);
        if (pixelShader == 0) {
            return 0;
        }

        int program = GLES20.glCreateProgram();
        if (program != 0) {
            GLES20.glAttachShader(program, vertexShader);
            GLUtil.checkGlError(TAG, "glAttachShader");
            GLES20.glAttachShader(program, pixelShader);
            GLUtil.checkGlError(TAG, "glAttachShader");
            GLES20.glLinkProgram(program);
            int[] linkStatus = new int[1];
            GLES20.glGetProgramiv(program, GLES20.GL_LINK_STATUS, linkStatus, 0);
            if (linkStatus[0] != GLES20.GL_TRUE) {
                Log.e(TAG, "Could not link program: ");
                Log.e(TAG, GLES20.glGetProgramInfoLog(program));
                GLES20.glDeleteProgram(program);
                program = 0;
            }
        }
        return program;
    }
}
