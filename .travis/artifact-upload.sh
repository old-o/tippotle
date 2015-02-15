#! /bin/bash

for file in $TRAVIS_BUILD_DIR/typepad/typepad-dist/target/*.zip
do
  curl -T "$file" \
       -u $FTP_USER:$FTP_PASSWORD \
       'sftp://doepner.net/~/public_html/dev/dist/ci-builds/'
done
