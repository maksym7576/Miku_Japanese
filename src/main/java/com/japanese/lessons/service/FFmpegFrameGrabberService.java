package com.japanese.lessons.service;

import org.bytedeco.ffmpeg.global.avcodec;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.FFmpegFrameRecorder;
import org.bytedeco.javacv.Frame;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.file.Files;

@Service
public class FFmpegFrameGrabberService {

    public ResponseEntity<byte[]> extractAudioFromVideo(String videoUrl) {
        File audioFile = null;
        try {
            audioFile = File.createTempFile("audio", ".wav");

            try (FFmpegFrameGrabber grabber = new FFmpegFrameGrabber(videoUrl)) {
                grabber.setFormat("mp4");
                grabber.start();
                try (FFmpegFrameRecorder recorder = new FFmpegFrameRecorder(audioFile, grabber.getAudioChannels())) {
                    recorder.setFormat("wav");
                    recorder.setSampleRate(grabber.getSampleRate());
                    recorder.setAudioCodec(avcodec.AV_CODEC_ID_PCM_S16LE);
                    recorder.start();

                    Frame frame;
                    while ((frame = grabber.grabSamples()) != null) {
                        recorder.record(frame);
                    }
                    recorder.stop();
                }
                grabber.stop();
            }
            byte[] audioBytes = Files.readAllBytes(audioFile.toPath());

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType("audio/wav"));
            headers.setContentDispositionFormData("attachment", "extracted-audio.wav");
            return new ResponseEntity<>(audioBytes, headers, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } finally {
            if(audioFile != null && audioFile.exists()) {
                audioFile.delete();
            }
        }
    }
}
