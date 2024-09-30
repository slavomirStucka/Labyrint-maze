% Cvičenie č. 11:

clear; 
clc;

% Načítanie a zapisovanie údajov do *.xlsx súborov

NameOfFile = ['Zadanie-StatistikaPopisna-02.xls']; 
FPath = fullfile('InputFiles',NameOfFile); 
[status,sheets] = xlsfinfo(FPath)         % informácie o hárkoch - ich názvy
% sheets = sheetnames(FPath);             % informácie o hárkoch - ich názvy (transponované)
% sheet1 = sheets{1};
% sheet2 = sheets{2};
% sheet3 = sheets{3};
[num,txt,raw] = xlsread(FPath);           % informácie o dátach

NameOfFile1 = ['Zadanie-StatistikaPopisna-02.csv'];
FPath1 = fullfile('InputFiles',NameOfFile1);

% Načítajte z hárka 'VstupneHodnoty' číselné hodnoty z matice 'B4:L24'.

sheet = sheets{1,2};
TRange1 = 'B4:L24'; % alebo sa udajú čísla stlpcov a riadkov rohov oblasti  [r1 c1 r2 c2] = [4 3 24 12]

DataA = readmatrix(FPath,'Sheet',sheet,'Range',TRange1)
A1 = readmatrix(FPath1,'DecimalSeparator',',')
A2 = xlsread(FPath,sheet,TRange1)   % starý príkaz na xls súbory

% Načítajte z hárka 'VstupneHodnoty' číselné hodnoty zo stĺpca P.

sheet = sheets{1,2};
TRange2 = 'P:P';  % celý stĺpec P.

u = readmatrix(FPath,'Sheet',sheet,'Range',TRange2);
u1 = xlsread(FPath,sheet,TRange2);
disp('u = ');
disp(u);
disp('u1 = ');
disp(u1');

% Načítajte z hárka 'Zadanie' hodnoty zo sedemnásteho riadku.

sheet = sheets{1,1};
TRange3 = '17:17';  % celý riadok 17.

v = readmatrix(FPath,'Sheet',sheet,'Range',TRange3);
v1 = xlsread(FPath,sheet,TRange3);
disp('v');
disp(v);
disp('v1');
disp(v1);

% Timetable                 --> readtimetable
% Numeric Matrix            -->	readmatrix
% Cell Array                -->	readcell
% Separate Column Vectors   --> readvars

% Výpočty a analýza údajov

DataASort = sort(DataA(:));
A = rmmissing(DataASort);

n = length(A);
% R = A(n) - A(1)
R = range(A,'all');
m = R+1;

First = A(1);
Last = A(n);
I = [1:m];              % indexy
x = [First:1:Last]; 
ni = zeros(1,m);
for i = 1:m
   for j = 1:n
      if (A(j) == x(i))
          ni(i) = ni(i) + 1;
      end
   end
end

N = zeros(1,m);
N(1) = ni(1);
for i = 2:m
   N(i) =  N(i-1) + ni(i);
end
f = zeros(1,m);
f = ni./n;

F = zeros(1,m);
F = N./n;

Tabulka = [I; x; ni; N; f; F]'

% Výpočet základných charakteristík (kontrola študentských výpočtov)

priemer = mean(A,'all')
modus = mode(A,'all')
median = median(A,'all')
rozptyl = var(A,1,'all')
sodchylka = std(A,1,'all')

% Spracovanie veľkého súboru pomocou triednych intervalov

pause(5);
clc;

NameOfFile = ['Zadanie-StatistikaPopisna-03.xls']; 
FPath = fullfile('InputFiles',NameOfFile); 
[status,sheets] = xlsfinfo(FPath); 

NameOfFile1 = ['Zadanie-StatistikaPopisna-03.csv'];
FPath1 = fullfile('InputFiles',NameOfFile1);

% Načítajte z hárka 'VstupneHodnoty' číselné hodnoty z matice 'B4:L24'.

sheet = sheets{1,2};
TRange1 = 'B4:L24'; 

DataA = readmatrix(FPath,'Sheet',sheet,'Range',TRange1)
A1 = readmatrix(FPath1,'DecimalSeparator',',')
 
% Výpočty a analýza údajov

DataASort = sort(DataA(:));
A = rmmissing(DataASort);

n = length(A);
% R = A(n) - A(1)
R = range(A,'all');
% m = 12;
m = 10;
h = R/m;

First = A(1);
Last = First + (m-1)*h;
I = [1:m];              % indexy
ai = [First:h:Last];    % dolné hranice triedneho intervalu
bi = ai + h;            % horné hranice triedneho intervalu
xi = (ai + bi)/2;       % reprezentant triedy

N = zeros(1,m);         % absolútna kumulatívna početnosť početnosť
for i = 1:m
   for j = 1:n
       if (A(j) <= bi(i))
          N(i) = N(i) + 1; 
       end
   end
end

ni = zeros(1,m);        % absolútna početnosť početnosť
ni(1) = N(1);
for i = 2:m
   ni(i) = N(i) - N(i-1); 
end

f = zeros(m,1);         % relatívna početnosť
f = ni./n;

F = zeros(m,1);         % relatívna kumulatívna početnosť
F = N./n;

Tabulka = [I; ai; bi; xi; ni; N; f; F]'

% Výpočet základných charakteristík
% Aritmetický priemer
Priemer = (1/n)*ni*xi'

% Modus
[M,index] = max(ni);
d1 = ni(index) - ni(index - 1);
d2 = ni(index) - ni(index + 1);
Modus = ai(index) + h*(d1/(d1 + d2))

% Medián
index = 0;
for i = 1:m
   if (N(i) > n/2)
       index = i;
       break
   end
end
Median = ai(index) + h*(((n+1)/2 - N(index-1))/ni(index))

% Priemerná odchýlka
d = (1/n)*sum(ni.*abs(xi-Priemer))

% Rozpzyl
s2 = (1/n)*(ni*((xi - Priemer).^2)')

% Smerodajná odchýlka
s = sqrt(s2)

% Centrálne momenty
Mi3 = (1/n)*sum(ni*(xi - Priemer)'.^3)
Mi4 = (1/n)*(ni*((xi - Priemer)'.^4))

% Koeficient šikmosti
Gama3 = Mi3/s^3

% Koeficient špicatosti (excesu)
Gama4 = Mi4/s^4 - 3

% Grafy v MATLAB-e - domáca úloha