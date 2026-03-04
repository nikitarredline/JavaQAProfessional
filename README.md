Для локального запуска тестов выполнить команду:  
mvn clean test

Для удаленного запуска тестов добавить параметры:  
-Dremote.url=http://&lt;host&gt;/wd/hub -Dbrowser.name=chrome -Dbrowser.version=128.0

Поддерживаемые браузеры:  
- chrome 128.0
- chrome 127.0

Для удаленного запуска тестов с мобильной эмуляцией также добавить параметр:  
-DdeviceName=&lt;device&gt;

Список поддерживаемых устройств:  
- iPhoneX  
- iPhone8  
- iPhone8Plus  
- GalaxyS5  
- Pixel2  

Для эмуляции кастомного разрешения при мобильной эмуляции использовать параметр:  
-Dbrowser.windowSize=<WIDTH,HEIGHT>
