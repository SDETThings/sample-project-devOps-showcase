#!/bin/bash
#-------------------------------------------------------------------
#  This script expects the following environment variables
#     HUB_HOST
#     BROWSER
#     THREAD_COUNT
#     TEST_SUITE
#-------------------------------------------------------------------
# Let's print what we have received
echo "-------------------------------------------"
echo "HUB_HOST      : ${HUB_HOST:-selenoid}"
echo "BROWSER       : ${BROWSER:-chrome}"
echo "THREAD_COUNT  : ${THREAD_COUNT:-2}"
echo "TEST_SUITE    : ${TEST_SUITE}"
echo "-------------------------------------------"
# Do not start the tests immediately. selenoid hub has to be ready with browser nodes
echo "Checking if hub is ready..!"
count=0
while [ "$( curl -s http://${HUB_HOST:-selenoid}:4444/status | jq -r .value.ready )" != "true" ]
do
  count=$((count+1))
  echo "Attempt: ${count}"
  if [ "$count" -ge 60 ]
  then
      echo "**** HUB IS NOT READY WITHIN 60 SECONDS ****"
      exit 1
  fi
  sleep 1
done
# At this point, selenium grid should be up!
echo "Selenoid hub is up and running. Running the test...."

# Debug: show the available test suite files
ls -l test-suites/

# Start the java command
java -cp 'libs/*' \
     -Dselenoid.enabled=true \
     -Dselenoid.hubHost="${HUB_HOST:-selenoid}" \
     -DBrowser="${BROWSER:-chrome}" \
     org.testng.TestNG \
     -threadcount "${THREAD_COUNT:-1}" \
     test-suites/"${TEST_SUITE}"