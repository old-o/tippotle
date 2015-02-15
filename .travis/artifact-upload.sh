#! /bin/bash -x

for file in $TRAVIS_BUILD_DIR/typepad-dist/target/*.zip
do
  curl -T "$file" \
       -u $FTP_USER:$FTP_PASSWORD \
       'ftp://doepner.net/~/public_html/dev/dist/ci-builds/'
done
