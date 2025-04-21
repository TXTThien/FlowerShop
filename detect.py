import sys
import cv2
import numpy as np
from ultralytics import YOLO
from collections import Counter
from PIL import Image
import json
# Load mô hình YOLO
model_path = r"C:\Users\DELL\FlowerShop\best.pt"
model = YOLO(model_path)

# Đọc đường dẫn ảnh từ tham số
image_path = sys.argv[1]
image = Image.open(image_path)
image_np = cv2.cvtColor(np.array(image), cv2.COLOR_RGB2BGR)

# Chạy nhận diện
results = model(image_np)

# Đếm đối tượng
detected_objects = []

for result in results:
    boxes = result.boxes
    for box in boxes:
        x1, y1, x2, y2 = map(int, box.xyxy[0])
        class_id = int(box.cls[0])
        label = result.names[class_id]
        detected_objects.append(label)
        cv2.rectangle(image_np, (x1, y1), (x2, y2), (0, 255, 0), 4)  # dày hơn (2 → 4)

        # Điều chỉnh kích thước chữ và độ dày nét
        font_scale = 2.0  # ← tăng lên từ 0.5 → 2.0 (hoặc cao hơn tùy bạn)
        thickness = 2

        # Tính toán lại vị trí y để tránh chữ nằm ngoài ảnh
        text_size, _ = cv2.getTextSize(label, cv2.FONT_HERSHEY_SIMPLEX, font_scale, thickness)
        text_y = max(y1 - 10, text_size[1])

        cv2.putText(
            image_np,
            label,
            (x1, text_y),
            cv2.FONT_HERSHEY_SIMPLEX,
            font_scale,
            (0, 0, 255),  # màu chữ đỏ (dễ nhìn hơn)
            thickness
        )
output_path = "output.jpg"
cv2.imwrite(output_path, image_np)

# Đếm số lượng mỗi loại
counts = Counter(detected_objects)
print(json.dumps(counts, ensure_ascii=False))
print("IMAGE_PATH:" + output_path)

# In ra kết quả (trả về cho Java)
print("Kết quả nhận diện:")
for obj, count in counts.items():
    print(f"{obj}")


