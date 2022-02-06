import mysql.connector
import csv
import sqlite3

mydb = sqlite3.connect("/home/toxity/Documents/uni/m4/android/data/imdb.db")
print(mydb)
cursor = mydb.cursor()
res = cursor.execute(" select * from basic_movie")
print("result" +  str(res))
final_query = "INSERT INTO basic_movie(  id, title, prime_title,orig_title, is_adult, rel_date, end_date, runtime, genre_1, genre_2, genre_3)"
cntr = 0
i = 1
if i == 1:
    with open("data.tsv") as file:
        tsv_file = csv.reader(file, delimiter="\t")
        for line in tsv_file:
            print(cntr)
            cntr = cntr + 1
            #print("line = " + str(line))
            #for li in line:
            li = line[0].replace(", ", "%%%%")
            #print("li =" + str(li))
            words = li.split(",")
            if cntr > 32000:
                if len(words) >= 11:
                   # print(str(words) + str(len(words)))
                    for i in range(10):
                        words[i] = words[i].replace("'","''")

                #print(words)
                #print(words[0:11])
                #print(words[0])
                    final_values = " VALUES('" + words[0] + "','" + words[1] + "','" + words[2] + "','" + \
                                   words[3] + "','" + words[4] + "','" + words[5] + "','" + words[6] + "','" + words[
                                       7] + "','" + words[8] + "','" + words[9] + "','" + words[10] + "')"
                    #print(final_values)
                    #print(final_query+final_values)
                    res=cursor.execute(final_query+final_values)
                    mydb.commit()
            #print(res)

    mydb.close()
