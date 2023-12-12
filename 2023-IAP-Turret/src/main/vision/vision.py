from networktables import NetworkTables
import cv2
import numpy as np

# Initialize NetworkTables
NetworkTables.initialize(server='roborio-3341-frc.local')
table = NetworkTables.getTable('datatable')

def get_target_center():
    # Initialize the camera capture (assuming cv2.VideoCapture is used)
    cap = cv2.VideoCapture(0)  # Use the appropriate camera index
    
    pipeline = ConeGripPipeline()  # Create an instance of your pipeline

    while True:
        ret, frame = cap.read()  # Capture frame from the camera

        # Process the frame using the pipeline
        pipeline.process(frame)

        # Access the center coordinates of the identified target
        contours = pipeline.find_contours_output  # Get contours from the pipeline
        
        # Perform calculations to determine the center of the target using contours or other methods
        # (Assuming contours contain the necessary information for target center calculation)
        target_center_x, target_center_y = calculate_target_center(contours)

        # Update NetworkTables with the target center coordinates
        table.putNumber('target_center_x', target_center_x)
        table.putNumber('target_center_y', target_center_y)

        # Display the processed frame (optional)
        cv2.imshow('Processed Frame', frame)

        if cv2.waitKey(1) & 0xFF == ord('q'):
            break

    cap.release()
    cv2.destroyAllWindows()

def calculate_target_center(contours):
    # Initialize an empty center tuple
    center = (0, 0)

    if len(contours) > 0:
        # Find the largest contour assuming it represents the target
        largest_contour = max(contours, key=cv2.contourArea)

        # Calculate the moments of the largest contour
        M = cv2.moments(largest_contour)

        if M["m00"] != 0:
            # Calculate the centroid coordinates
            center_x = int(M["m10"] / M["m00"])
            center_y = int(M["m01"] / M["m00"])

            # Update the center tuple
            center = (center_x, center_y)

    return center

if