#!/bin/bash

output_csv="/home/rebelofdeath/research/SF110/LOCs.csv"

echo "Class,Project,Lines,String_Char_Count,Number_Types_Count" > "$output_csv"

find /home/rebelofdeath/research/SF110/SF110 -name "*.java" ! -name "*Test.java" | while read java_file; do
    class_name=$(basename "$java_file" .java)

    project_name=$(echo "$java_file" | awk -F'/' '{print $(NF-3)}')

    line_count=$(wc -l < "$java_file")

    string_char_count=$(grep -o -E 'String|char' "$java_file" | wc -l)

    number_types_count=$(grep -o -E 'Integer|Double|double|integer|byte' "$java_file" | wc -l)

    echo "\"$class_name\",\"$project_name\",\"$line_count\",\"$string_char_count\",\"$number_types_count\"" >> "$output_csv"
done

echo "CSV file generation complete: $output_csv"
