#!/bin/bash

export JDK_JAVA_OPTIONS="-Djdk.attach.allowAttachSelf=true"

direction=$1
javaVersion=$2

if [ "$2" = "8" ]; then
  sdk default java 8.0.333.fx-librca
else
  sdk default java 11.0.17-tem
fi

cd "$direction"

echo "RUNNING ON THE $direction DATASET"

if [ $# -lt 4 ]; then
  fileDirectory="classes"
else
  fileDirectory="$4-classes"
fi

while IFS="," read -r proj class
do
  echo "Project: $proj"
  echo "Class: $class"
  echo ""

  cd "$proj"
  logDir="log/"

  mkdir -p $logDir
  source evosuite-files/evosuite.properties

  if [ "$3" = "true" ]; then
      inheritanceFlag="-Dinheritance_file=$inheritance_file"
  else
      inheritanceFlag=""
  fi

  java -jar ../../utestgen-$javaVersion.jar -projectCP "$CP" -class $class -Dcriterion=BRANCH:LINE:OUTPUT:METHOD:CBRANCH $inheritanceFlag -Dtest_naming_strategy=llm_based -Dvariable_naming_strategy=HEURISTICS_BASED -Dassertion_timeout=100000 -Dsearch_budget=200 -Dminimization_timeout=100000 -Dwrite_junit_timeout=100000 -Dextra_timeout=10000 -Ddefuse_debug_mode=true -Dtest_format=JUNIT5LLM -Djunit_check_timeout=10000 ${flags} -Dbytecode_logging_mode=FILE_DUMP > $logDir/$class-$(date '+%Y-%m-%d-%H-%M').log

  cd ../
done < <(tail -n +2 $fileDirectory.csv)
