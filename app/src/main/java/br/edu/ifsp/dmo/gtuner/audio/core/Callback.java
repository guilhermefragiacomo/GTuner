package br.edu.ifsp.dmo.gtuner.audio.core;

public interface Callback {
    void onBufferAvailable(byte[] buffer);
}