package sample;

import java.io.*;
import java.util.Arrays;

import javafx.concurrent.Task;
import javax.sound.sampled.*;

/**
 *
 */


public class JavaSoundCapture extends Task<Void>
{

    private Mediator mediator;
    JavaSoundCapture(Mediator mediator)
    {
        this.mediator = mediator;
    }
    // format of audio file
    // the line from which audio data is captured
    TargetDataLine line;
    byte[] dataVector = new byte[16384];


    @Override
    protected Void call() throws Exception
    {
        try {

            // use only 1 channel, to make this easier
            final AudioFormat format = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, 44100, 16, 1, 2, 44100, true);
            final DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);

            // checks if system supports the data line
            if (!AudioSystem.isLineSupported(info)) {
                System.out.println("Line not supported");
                System.exit(0);
            }
            line = (TargetDataLine) AudioSystem.getLine(info);
            line.open(format, line.getBufferSize());
            line.start();   // start capturing

            System.out.println("Start capturing...");



            try {

                int av;
                int num;
                int sample;
                float[] sampleVector = new float[8192];

                while (true) {
                    if ((av = line.available()) > 0) {
                        num = line.read(dataVector, 0, 16384);
                        for (int i = 0; i < 8192; i++) {
                            sampleVector[i] =
                                    dataVector[i * 2 + 1] & (dataVector[i * 2] << 8);
                        }
                        this.mediator.UpdateVector(sampleVector);
                    }
                }

            } catch (Exception e) {
            }
        }catch (Exception ex)
        {
            ex.printStackTrace();
        }
            //AudioInputStream ais = new AudioInputStream(line);

            //System.out.println("Start recording...");

            //final byte[] buf = new byte[256]; // <--- increase this for higher frequency resolution
            //final int numberOfSamples = buf.length / format.getFrameSize();
            //int i=0;
            //while (true) {
                // in real impl, don't just ignore how many bytes you read
                //ais.read(buf);
                // the stream represents each sample as two bytes -> decode
                //i++;

                //System.out.println( String.format("%02x", buf[0]) + String.format("%02x", buf[1])+String.format("%02x", buf[2])+String.format("%02x", buf[1]));
                //float audioVal = (float) (((buf[1] << 8)
                //        | (buf[0] & 0xff)) / 32768.0);
                //Thread.sleep(1000);
            //}

        //}
        //catch (LineUnavailableException | IOException ex) {
        //    ex.printStackTrace();
        //}

        return null;
    }

    /**
     * Defines an audio format
     */
    AudioFormat getAudioFormat()
    {
        float sampleRate = 16000;
        int sampleSizeInBits = 8;
        int channels = 2;
        boolean signed = true;
        boolean bigEndian = true;
        AudioFormat format = new AudioFormat(sampleRate, sampleSizeInBits,
                channels, signed, bigEndian);
        return format;
    }

    /**
     * Closes the target data line to finish capturing and recording
     */
    void finish()
    {
        line.stop();
        line.close();
        System.out.println("Finished");
    }
}
