/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jna;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.win32.StdCallLibrary;
import com.sun.jna.win32.W32APIOptions;

/**
 *
 * @author bowen
 */
public interface Graphics extends StdCallLibrary {
    Graphics INSTANCE = (Graphics)Native.loadLibrary("system.drawing", Graphics.class, W32APIOptions.DEFAULT_OPTIONS);
    Graphics FromHdc(Pointer hdc);
}
