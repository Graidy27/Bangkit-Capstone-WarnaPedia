# Bangkit-Capstone-WarnaPedia
This repositories is for Warna-Pedia's API deployment.
## Steps to Deploy Warna Pedia API to Cloud Run with GCP 
### With Cloud Shell on GCP
1. Open Cloud Shell
2. Clone this Repository with branch Cloud-Computing <br>
```console
git clone --branch Cloud-Computing https://github.com/Graidy27/Bangkit-Capstone-WarnaPedia.git
```
3. Open the path with the desired version. example we use Versi 3 <br>
```console
cd Bangkit-Capstone-WarnaPedia\API\'Versi 3'
``` 
4. First make sure that all files have been cloned
5. create a container-images<br>
```console
docker built -t [your_name_container] .
```
```console
docker run -it -p 7777:7777 [your_name_container]
```
```console
docker tag [your_name_container] gcr.io/[id_project]/[your_name_container]
```
```console
docker push gcr.io/[id_project]/[your_name_container]
```
6.  Open Cloud Run Service And Create New Service with port 7777
