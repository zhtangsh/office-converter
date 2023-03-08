# Office to pdf Converter

- Based on JOD Converter && open office
- Convert office to pdf.

## Usage

### base image

Build base image with openjdk && open office

```bash
cd base-image
docker build . -t openjdk8-office:7.5
```

### application image

```bash
mvn clean package -Dmaven.test.skip=true
cd oc-basic
docker build . -t oc:dev
docker run -d -p 8800:8800 oc:dev
```