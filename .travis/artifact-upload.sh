#! /bin/bash

ftp_file_url='ftp://doepner.net/~/public_html/dev/dist/ci-builds/typepad.zip'

for file in $TRAVIS_BUILD_DIR/typepad-dist/target/*.zip ; do
  echo "Uploading $file to $ftp_file_url"
  curl -T "$file" -u $FTP_USER:$FTP_PASSWORD "$ftp_file_url"
done
