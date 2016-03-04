#! /bin/bash

local_file="$(ls $TRAVIS_BUILD_DIR/dist/target/*.zip | head -n 1)"
target_url='https://api.bintray.com/content/odoepner/generic/tippotle/0.0.1/tippotle.zip?override=1&publish=1'

if [ -z "$local_file" ]; then
  echo "ERROR: Cannot upload. local_file is empty"
  exit 1
else
  echo "Uploading $local_file to $target_url"
  curl -u$BINTRAY_USER:$BINTRAY_API_KEY -T "$local_file" "$target_url"
fi
