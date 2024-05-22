# Sử dụng image của OpenJDK làm nền tảng
FROM openjdk:11

# Thiết lập biến môi trường cho Android SDK
ENV ANDROID_SDK_ROOT /usr/local/android-sdk-linux

# Cài đặt các gói cần thiết
RUN apt-get update && \
    apt-get install -y wget unzip && \
    rm -rf /var/lib/apt/lists/*

# Tải và giải nén Android SDK Command Line Tools
RUN wget https://dl.google.com/android/repository/commandlinetools-linux-8512546_latest.zip -O /tmp/sdk.zip && \
    mkdir -p $ANDROID_SDK_ROOT/cmdline-tools && \
    unzip /tmp/sdk.zip -d $ANDROID_SDK_ROOT/cmdline-tools && \
    mv $ANDROID_SDK_ROOT/cmdline-tools/cmdline-tools $ANDROID_SDK_ROOT/cmdline-tools/latest && \
    rm /tmp/sdk.zip

# Thiết lập biến môi trường PATH
ENV PATH ${PATH}:${ANDROID_SDK_ROOT}/cmdline-tools/latest/bin:${ANDROID_SDK_ROOT}/platform-tools

# Chấp nhận các điều khoản và cài đặt các gói cần thiết của Android SDK
RUN yes | sdkmanager --licenses && \
    sdkmanager --update && \
    sdkmanager "platform-tools" "platforms;android-30" "build-tools;30.0.3"

# Sao chép mã nguồn vào container
COPY . /usr/src/app

# Thiết lập thư mục làm việc
WORKDIR /usr/src/app

# Đảm bảo gradlew có quyền thực thi
RUN chmod +x ./gradlew

# Lệnh để build ứng dụng
CMD ./gradlew build
