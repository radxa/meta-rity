#!/usr/bin/env python3
import cv2
import sys

face_cascade = cv2.CascadeClassifier('/usr/share/opencv4/haarcascades/haarcascade_frontalface_default.xml')
eye_cascade = cv2.CascadeClassifier('/usr/share/opencv4/haarcascades/haarcascade_eye.xml')
smile_cascade = cv2.CascadeClassifier('/usr/share/opencv4/haarcascades/haarcascade_smile.xml')

def detect(gray, frame):
    faces = face_cascade.detectMultiScale(gray, 1.3, 5)
    for (x, y, w, h) in faces:
        cv2.rectangle(frame, (x, y), ((x + w), (y + h)), (255, 0, 0), 2)
        roi_gray = gray[y:y + h, x:x + w]
        roi_color = frame[y:y + h, x:x + w]
        smiles = smile_cascade.detectMultiScale(roi_gray, 1.8, 20)
        for (sx, sy, sw, sh) in smiles:
            cv2.rectangle(roi_color, (sx, sy), ((sx + sw), (sy + sh)),
                          (0, 0, 255), 2)
    return frame

if len(sys.argv) < 2:
    print("Usage: " + sys.argv[0] + " filename\n")
    print("filename: cam_id          Camera ID. (e.g. 3)")
    print("          cam_device      Camera device. (e.g. /dev/video3)")
    print("          gst_pipeline    GStreamer pipeline. (e.g. \"v4l2src device=/dev/video3 ! videoconvert ! appsink\")")
    exit(-1)

filename = sys.argv[1]
if filename.isdigit():
    filename = int(sys.argv[1])

video_capture = cv2.VideoCapture(filename, cv2.CAP_V4L2)
if video_capture.isOpened() != True:
    print("Open video failed")
    exit(-1)

while True:
    # Captures video_capture frame by frame
    ret, frame = video_capture.read()
    if ret != True:
        print("Read frame failed")
        break

    # To capture image in monochrome
    gray = cv2.cvtColor(frame, cv2.COLOR_BGR2GRAY)

    # calls the detect() function
    canvas = detect(gray, frame)

    # Displays the result on camera feed
    cv2.imshow('Video', canvas)

    # The control breaks once q key is pressed
    if cv2.waitKey(1) & 0xff == ord('q'):
        break

# Release the capture once all the processing is done.
video_capture.release()
cv2.destroyAllWindows()
