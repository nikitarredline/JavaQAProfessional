для локального запуска тестов выполнить команду mvn clean test
для удаленного запуска тестов добавить параметр -Dbase.url=http://<host>:<port>/api -Dremote.url=http://<host>/wd/hub -Dbrowser.name=chrome -Dbrowser.version=128.0
поддерживаемые браузеры: chrome 128, chrome 127
для удаленного запуска тестов с мобильной эмуляцией также добавить параметр -DdeviceName=<device>
список поддерживаемых устройств:
          iPhoneX,
          iPhone8,
          iPhone8Plus,
          GalaxyS5,
          Pixel2
для эмуляции кастомного разрешения при мобильной эмуляции использовать параметр -Dbrowser.windowSize=<WIDTH,HEIGHT>
