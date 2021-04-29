how to run :
1. extract the zip file
2. open terminal
3. cd to directory\of\file
4. type "java T2_205150201111033_Gabrielle_Evan_Farrel" without the "" 

Available Command :
ADD = without # for adding new Node on script (html, head, etc), example = ADD;html;head;head where html is the Node name, first head as the child Node of html, and then last head as the id o of the head Node
ADD = with # for adding elements on existing root Node, example = ADD;#head;title;title1 where #head is the root where the new elements will be stored, title would be the element, and title1 is the id of the new element
SEARCH = with # for printing the content of specific selector like #head from before, example = SEARCH;#title1 would print the content od title1 and its parents, but not siblings
DELETE = with # to delete your created Node/element, example = DELETE;#title1
PRINT = without #, print the result of your command;

test case to try and understand the code:

ADD;html;head;head
ADD;#head;title;title1
ADD;TEXT;#title1;Halaman Profil
ADD;#html;body;body
ADD;#body;h1;head1
ADD;TEXT;#head1;Selamat datang
ADD;TEXT;#body;Ini elemen teks
ADD;#body;div;content
ADD;#content;p;p1
ADD;TEXT;#p1;Ini contoh paragraf pertama
ADD;#content;p;p2
ADD;TEXT;#p2;Ini contoh paragraf kedua
ADD;#content;a;a1
ADD;TEXT;#a1;Buka Google!
ADD;#content;div;content1
ADD;TEXT;#content1;Isi content1
ADD;#content1;p;p4
ADD;#body;p;p4
ADD;#body;div;footer
ADD;#footer;h1;footer-content
ADD;TEXT;#footer-content;Ini adalah footer
ADD;#footer;p;p3
PRINT;html;100
PRINT;html;1
PRINT;html;0
ADD;#header;div;div-header
ADD;div;p;paragraf
PRINT;div;1000
DELETE;#title1
PRINT;html;100
SEARCH;#p1
