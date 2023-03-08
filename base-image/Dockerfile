FROM adoptopenjdk/openjdk8:debian-jre
ENV TZ Asia/Shanghai
ENV LANG C.UTF-8
ENV LC_ALL C.UTF-8
ENV PATH /opt/libreoffice7.5/program:$PATH

COPY sources.list /etc/apt/

COPY fonts/ /usr/share/fonts/

ADD LibreOffice_7.5.0_Linux_x86-64_deb.tar.gz /tmp/

RUN apt-get update && apt-get install -y fontconfig ttf-mscorefonts-installer ttf-wqy-microhei ttf-wqy-zenhei xfonts-wqy && \
  apt-get install -y procps libxrender1 libxinerama1 libxt6 libxext-dev libfreetype6-dev libcairo2 libcups2 libx11-xcb1 libnss3 libglib2.0-dev && \
  dpkg -i /tmp/LibreOffice_7.5.0.3_Linux_x86-64_deb/DEBS/*.deb &&	rm -rf /tmp/LibreOffice_7.5.0.3_Linux_x86-64_deb && \
  rm -rf /var/lib/apt/lists/* && \
  cd /usr/share/fonts/ && mkfontscale && mkfontdir && fc-cache -fv