# Legt für jedes 50x50 Feld eine .txt Datei an, in welche dann
# die einzelnen Felder geschrieben werden. Vorbereitung für
# Day 22 Part 2.
#
# @author Yanik Recke

file = open("input.txt", "r")

linecounter = 0

# a -> appends
in1 = open("1.txt", "a")
in2 = open("2.txt", "a")
in3 = open("3.txt", "a")
in4 = open("4.txt", "a")
in5 = open("5.txt", "a")
in6 = open("6.txt", "a")

for line in file:
    line = line.lstrip()

    if linecounter < 50:
        counter = 0
        for char in line:
            if counter < 50:
                in1.write(char)
            else:
                if counter == 50:
                    in1.write("\n")

                in2.write(char)

            counter = counter + 1

        linecounter = linecounter + 1

    elif linecounter < 100:
        in3.write(line)
        linecounter = linecounter + 1

    elif linecounter < 150:
        counter = 0
        for char in line:
            if counter < 50:
                in5.write(char)
            else:
                if counter == 50:
                    in5.write("\n")

                in4.write(char)

            counter = counter + 1

        linecounter = linecounter + 1


    elif linecounter < 200:
        in6.write(line)
        linecounter = linecounter + 1