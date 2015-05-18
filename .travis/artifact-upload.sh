#! /bin/bash

local_file="$(ls $TRAVIS_BUILD_DIR/dist/target/*.zip | head -n 1)"
target_url='ftp://doepner.net/~/public_html/dev/dist/ci-builds/typepad.zip'

if [ -z "$local_file" ]; then
  echo "ERROR: Cannot upload. local_file is empty"
  exit 1
else
  echo "Uploading $local_file to $target_url"
  curl -u $FTP_USER:$FTP_PASSWORD -T "$local_file" "$target_url"
fi
