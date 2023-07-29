# Arduino & Phone as an HTTP Server for Remote Control
![Robot](https://github.com/abdulkadrtr/internetControlledRobot/assets/87595266/cae62f8c-5bb9-44a0-9728-fea0ddd40bf6)
## SensorShare: Arduino ile Telefon Arasında Köprü Kuran İnternet Bağlantılı Robot Projesi

## Proje Tanıtım Videosu & Demo 

https://youtu.be/pjNkS2eiwVk


### Proje Hakkında 
Arduino ve telefonunuz arasında bağlantı kuran, yenilikçi bir proje olan SensorShare kotlin dilinde yazılmış bir mobil uygulamadır. Bu uygulama telefonunuzu localhost port 8080 kullanarak bir HTTP  sunucusuna dönüştür. Basit GET istekleriyle `/gps` ,  `/orientation`, `/gyro`, `/accelerometer` ve `/light` adreslerinden telefonunuzun sensör verilerini kolayca almanızı sağlar. 

Asıl olay, `/control` adresine bir POST isteği gönderdiğinizde gerçekleşir. Bu istekle controlSignal adında bir int değer istek gövdesine ekleyebilir ve bu değeri doğrudan telefonunuzla OTG kablosu aracılığıyla bağlı olan arduino cihazına gönderebilirsiniz. Ayrıca bu proje, bu istekleri kolaylaştırmak ve sezgisel bir arayüz sağlama için HTML, CSS ve JavaScript kullanılarak tasarlanmış kullanıcı dostu bir Web tabanlı Yönetim Paneli içerir.

SensorShare, ekstra sensörler satın almadan internet bağlantılı robotlar oluşturmanın kapılarını aralar. Telefonunuzda zaten bulunan sensörleri kullanarak, düşük maliyetli bir robotik çözümü için bir fırsat sunar. 

Temel hedefimiz, kamikaze robotlar için ekonomik bir robotik çözüm sunmaktır.

### Proje Özellikleri
  - Telefon Sensörleri Kullanımı: Ekstra sensörler satın almadan, telefonunuzun bulunduğu sensörlerle robotunuzu donatın.
  - Anlık Veri İzleme: Yönetim paneli arayüzü sayesinde robotunuzun anlık sensör verilerini görebilir ve takip edebilirsiniz.
  - Video Akışı: Robotunuzun (telefonunuzun) kamerasından gelen video akışını yönetim panelinde izleyin ve çevreyi uzaktan gözlemleyin.
  - Konum İzleme: Yönetim paneli üzerindeki harita sayesinde robotunuzun anlık konumunu takip edin.
  - Arduino Kontrolü: Yönetim paneli sayesinde arduino cihazınıza uzaktan komut gönderin.
  - Uzaktan Erişim: Özel bir VPN ağı kullanarak dünya genelinden robotunuza erişin ve yönetin.

### Proje Kurulumu
  - SensorShare uygulamasının .apk versiyonunu indirin ve telefonunuza yükleyin. Sonrasında GPS izinlerini aktif edin.
  - Arduino cihazınızı OTG kablosu aracılığıyla telefonunuza bağlayın. Telefonunuzda OTG izinleri aktif olmalıdır.
  - Canlı video akışı için Play Store üzerinden DroidCam uygulamasını indirin.
  - Sensor Share uygulamasını açıp `Server Başlatıldı` uyarısı görene kadar bekleyin. Sonrasında DroidCam uygulamasına giderek ekranda gördüğünüz ip adresini Yönetim paneline girin ve bağlan tuşuna tıklayın. Bağlantı kurulduğunda yöetim paneli üzerinde bir bilgilendirme mesajı alırsınız ve yönetim paneli sürekli olarak yeni verilerle güncellenmeye başlar.
  - Bu aşamada projeyi özelleştirebilir. Arduino pinlerinden dilediğinizi kullanabilir ve manuel sinyal gönder alanını kullanarak arduino ile haberleşebilirsiniz. Örnek arduino kodları bu işlemler için rehber niteliğindedir.

### Robot Modeli
![robot](https://github.com/abdulkadrtr/internetControlledRobot/assets/87595266/4f791f5c-18c2-4ddd-9585-44dd323387fa)
### Yönetim Paneli Önizlemesi:
![Screenshot_1](https://github.com/abdulkadrtr/internetControlledRobot/assets/87595266/b74fcf18-eb25-4b8b-b26c-69625de1ee4a)

