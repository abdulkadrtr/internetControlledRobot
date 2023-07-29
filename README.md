# Arduino & Phone as an HTTP Server for Remote Control
## SensorShare: Arduino ile Telefon Arasında Köprü Kuran İnternet Bağlantılı Robot Projesi
### Proje Hakkında 
Arduino ve telefonunuz arasında bağlantı kuran, yenilikçi bir proje olan SensorShare kotlin dilinde yazılmış bir mobil uygulamadır. Bu uygulama telefonunuzu localhost port 8080 kullanarak bir HTTP  sunucusuna dönüştür. Basit GET istekleriyle `/gps` ,  `/orientation`, `/gyro`, `/accelerometer` ve `/light` adreslerinden telefonunuzun sensör verilerini kolayca almanızı sağlar. 

Asıl olay, `/control` adresine bir POST isteği gönderdiğinizde gerçekleşir. Bu istekle controlSignal adında bir int değer istek gövdesine ekleyebilir ve bu değeri doğrudan telefonunuzla OTG kablosu aracılığıyla bağlı olan arduino cihazına gönderebilirsiniz. Ayrıca bu proje, bu istekleri kolaylaştırmak ve sezgisel bir arayüz sağlama için HTML, CSS ve JavaScript kullanılarak tasarlanmış kullanıcı dostu bir Web tabanlı Yönetim Paneli içerir.

SensorShare, ekstra sensörler satın almadan internet bağlantılı robotlar oluşturmanın kapılarını aralar. Telefonunuzda zaten bulunan sensörleri kullanarak, düşük maliyetli bir robotik çözümü için bir fırsat sunar. 

Temel hedefimiz, kamikaze robotlar için ekonomik bir robotik çözüm sunmaktır.

### Proje Bileşenleri

