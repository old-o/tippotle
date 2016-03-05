#! /bin/bash

build_dir="$TRAVIS_BUILD_DIR/dist/target"
zip_file="$(ls $build_dir/*.zip | head -n 1)"

upload_url='https://api.bintray.com/content/odoepner/generic/tippotle/0.0.1/tippotle.zip?override=1&publish=1'

if [ -z "$local_file" ]; then
  echo "ERROR: Cannot upload. No zip file found in $build_dir"
  exit 1
else
  echo "Uploading $local_file to $upload_url"
  curl -u$BINTRAY_USER:$BINTRAY_API_KEY -T "$local_file" "$upload_url"
fi
