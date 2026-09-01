"""Additional EN|RU pairs so every WordGuesser pack has 80–150 words."""

from __future__ import annotations


def pairs(block: str) -> list[tuple[str, str]]:
    out: list[tuple[str, str]] = []
    seen_en: set[str] = set()
    seen_ru: set[str] = set()
    for raw in block.strip().splitlines():
        line = raw.strip()
        if not line or line.startswith("#"):
            continue
        en, ru = (part.strip() for part in line.split("|", 1))
        key_en, key_ru = en.casefold(), ru.casefold()
        if key_en in seen_en or key_ru in seen_ru:
            raise SystemExit(f"Duplicate in extras: {en} | {ru}")
        seen_en.add(key_en)
        seen_ru.add(key_ru)
        out.append((en, ru))
    return out


MORE: dict[tuple[str, str | None], list[tuple[str, str]]] = {
    ("animals", "easy"): pairs("""
lamb|ягнёнок
calf|телёнок
foal|жеребёнок
chick|цыплёнок
duckling|утёнок
gosling|гусёнок
cub|медвежонок
puppy dog|пёсик
kitten cat|котик
reindeer|северный олень
polar bear|белый медведь
brown bear|бурый медведь
white shark|белая акула
goldfish bowl|аквариумная рыбка
canary|канарейка
pigeon|голубь
dove|горлица
swallow|ласточка
magpie|сорока
wood louse|мокрица
earthworm|дождевой червь
slug|слизень
centipede|сороконожка
ladybird|божья коровка красная
wasp nest|осиное гнездо
beehive|улей
silkworm|шелкопряд
moth|моль
fire ant|огненный муравей
dragonfly nymph|личинка стрекозы
    """),
    ("animals", "medium"): pairs("""
pronghorn|вилорог
caribou|карибу
gazelle|газель
impala|импала
wildebeest|гну
antelope|антилопа
ibis|ибис
crane bird|журавль
swan goose|гусь-лебедь
cormorant|баклан
puffin|тупик
albatross chick|птенец альбатроса
gecko|геккон
skink|сцинк
monitor lizard|варан
salamander|саламандра
newt|тритон
tree frog|древесная лягушка
bullfrog|лягушка-бык
stingray|скат-хвостокол
moray eel|мурена
clownfish|рыба-клоун
swordfish|рыба-меч
tuna|тунец
salmon|лосось
trout|форель
pike|щука
perch|окунь
carp|карп
catfish river|речной сом
squid|кальмар
cuttlefish|каракатица
nautilus|наутилус
hermit crab|рак-отшельник
barnacle|морская уточка
sea urchin|морской ёж
sea cucumber|морской огурец
coral polyp|полип
    """),
    ("animals", "hard"): pairs("""
quokka|квокка
quoll|куол
numbat|нумбат
tasmanian devil|тасманийский дьявол
cassowary|казуар
emu|эму
kookaburra|кукабарра
bowerbird|шалашник
shoebill|китоглав
secretary bird|секретарь
hoatzin|гоацин
kiwi bird|киви-птица
kakapo|какапо
tuatara|гаттерия
olingo|олинго
kinkajou|кинкажу
coati|носуха
binturong|бинтуронг
fossa|фосса
aye-aye|ай-ай
tarsier|долгопят
slow loris|толстый лори
gibbon|гиббон
siamang|сиаманг
proboscis monkey|носач
howler monkey|ревун
capuchin|капуцин
marmoset|игрунка
uakari|уакари
saiga|сайгак
markhor|винторогий козёл
takin|такин
gerenuk|жирафовая газель
dik-dik|дикдик
addax|аддакс
oryx|орикс
eland|канна
kudu|куду
nyala|ньяла
okapi calf|детёныш окапи
pangolin scale|чешуя панголина
axolotl gill|жабры аксолотля
narwhal tusk|бивень нарвала
    """),
    ("food", "easy"): pairs("""
breakfast|завтрак
lunch|обед
dinner|ужин
snack|перекус
toast|тост
cereal|хлопья
cornflakes|кукурузные хлопья
omelette easy|яичница
fried egg|яичница-глазунья
boiled egg|варёное яйцо
scrambled eggs|омлет
mashed potatoes|пюре
fried chicken|жареная курица
kebab|кебаб
pizza slice|кусок пиццы
cheeseburger|чизбургер
nugget|наггетс
corn|кукуруза
peas|горошек
beans|фасоль
cabbage|капуста
beet|свёкла
radish|редис
lemon|лимон
lime|лайм
cherry|вишня
plum|слива
melon|дыня
kiwi fruit|киви фрукт
coconut|кокос
walnut|грецкий орех
hazelnut|фундук
almond|миндаль
peanut|арахис
sunflower seeds|семечки
ketchup|кетчуп
mayonnaise sauce|майонезный соус
salt|соль
pepper|перец
sugar|сахар
    """),
    ("food", "medium"): pairs("""
carbonara|карбонара
bolognese|болоньезе
pesto|песто
minestrone|минестроне
gazpacho soup|гаспачо-суп
miso|мисо
udon|удон
soba|соба
pho soup|суп фо
pad thai|пад тай
spring roll|спринг-ролл
samosa|самоса
naan|наан
curry|карри
tikka masala|тикка масала
biryani|бирьяни
paella rice|паэлья с рисом
tapas|тапас
paella pan|сковорода для паэльи
brie|бри
camembert|камамбер
mozzarella|моцарелла
parmesan|пармезан
feta|фета
gouda|гауда
cheddar|чеддер
ricotta|рикотта
mascarpone|маскарпоне
prosciutto|прошутто
salami|салями
chorizo|чоризо
tartare steak|тартар
carpaccio beef|карпаччо из говядины
bruschetta|брускетта
caprese|капрезе
greek salad|греческий салат
caesar salad|цезарь
olivier salad|оливье
vinaigrette salad|винегрет
compote drink|компот
kvass|квас
    """),
    ("food", "hard"): pairs("""
bouillabaisse|буйабес
consomme|консоме
veloute|велуте
beurre blanc|бёр блан
hollandaise|голландский соус
bearnaise|бернез
romesco|ромеско
chimichurri|чимичурри
harissa|харисса
zaatar|заатар
sumac|сумак
fenugreek|пажитник
asafoetida|асафетида
galangal|галангал
lemongrass|лемонграсс
kaffir lime|кафир-лайм
yuzu|юдзу
shiso|шисо
nori|нори
wakame|вакаме
bonito flakes|стружка тунца
dashi|даси
gochujang|кочудян
doenjang|твенджан
szechuan pepper|сычуаньский перец
five spice|пять специй
star anise|бадьян
clove spice|гвоздика
nutmeg|мускатный орех
mace spice|мускатный цвет
juniper|можжевельник
vermouth|вермут
amaretto|амаретто
limoncello|лимончелло
sake|саке
soju|соджу
mezcal|мескаль
grappa|граппа
calvados|кальвадос
armagnac|арманьяк
    """),
    ("professions", "easy"): pairs("""
chef|шеф-повар
driver bus|водитель автобуса
train driver|машинист
stewardess|стюардесса
lifeguard|спасатель на воде
swimmer pro|пловец
footballer|футболист
hockey player|хоккеист
tennis player|теннисист
coach school|тренер школы
janitor|дворник
gardener park|садовник парка
florist|флорист
butcher|мясник
baker shop|пекарь в пекарне
barista cafe|бариста в кафе
waiter cafe|официант кафе
cook canteen|повар столовой
nurse clinic|медсестра клиники
doctor clinic|врач поликлиники
dentist clinic|стоматолог клиники
pharmacist shop|аптекарь
optician|окулист
vet clinic|ветеринар клиники
zoo keeper|смотритель зоопарка
farmer field|фермер на поле
milkmaid|доярка
shepherd|пастух
fisherman sea|морской рыбак
lumberjack|лесоруб
    """),
    ("professions", "medium"): pairs("""
oncologist|онколог
neurologist|невролог
therapist|терапевт
radiologist|рентгенолог
lab technician|лаборант
paramedic ambulance|фельдшер скорой
firefighter chief|начальник пожарных
police detective|полицейский сыщик
crime investigator|следователь
prosecutor|прокурор
defense attorney|адвокат защиты
bailiff|судебный пристав
prison guard|надзиратель
social worker|соцработник
hr manager|эйчар
project manager|проект-менеджер
product manager|продакт-менеджер
ux designer|ux-дизайнер
web developer|веб-разработчик
data analyst|аналитик данных
qa engineer|тестировщик
devops|девопс
sysadmin office|системный администратор
network engineer|сетевой инженер
electrical engineer|инженер-электрик
civil engineer|инженер-строитель
mechanical engineer|инженер-механик
chemical engineer|инженер-химик
interior designer|дизайнер интерьера
fashion designer|модельер
    """),
    ("professions", "hard"): pairs("""
hepatologist|гепатолог
endocrinologist|эндокринолог
rheumatologist|ревматолог
hematologist|гематолог
nephrologist|нефролог
pulmonologist|пульмонолог
gastroenterologist|гастроэнтеролог
immunologist|иммунолог
epidemiologist|эпидемиолог
virologist|вирусолог
parasitologist|паразитолог
toxicologist|токсиколог
forensic scientist|криминалист
ballistics expert|баллист
graphologist|графолог
numismatist|нумизмат
philatelist|филателист
lexicographer|лексикограф
epigraphist|эпиграфист
papyrologist|папиролог
codicologist|кодиколог
restorer fresco|реставратор фресок
gilder artisan|позолотчик
cooper|бондарь
wheelwright|колесник
millwright|механик мельницы
thatched roof|крыша из соломы
thatcher|кровельщик соломы
farrier|коваль
saddler|шорник
tanner|кожевник
weaver|ткач
spinner|прядильщик
dyer|красильщик
    """),
    ("sports", "easy"): pairs("""
football match|футбольный матч
goal|гол
penalty|пенальти
corner kick|угловой
offside|офсайд
referee|судья
whistle|свисток
stadium|стадион
fan|болельщик
jersey|футболка команды
cleats|бутсы
goalkeeper|вратарь
forward|нападающий
defender|защитник
midfielder|полузащитник
coach team|тренер команды
warmup|разминка
stretching|растяжка
push-up|отжимание
sit-up|пресс
plank|планка
squat|приседание
dumbbell|гантель
barbell|штанга
treadmill|беговая дорожка
pool|бассейн
lane|дорожка
finish line|финиш
medal|медаль
trophy|кубок
champion|чемпион
olympics|олимпиада
world cup|чемпионат мира
tournament|турнир
scoreboard|табло
timeout|тайм-аут
substitution|замена
fair play|честная игра
doping|допинг
record|рекорд
    """),
    ("sports", "medium"): pairs("""
hat-trick|хет-трик
own goal|автогол
yellow card|жёлтая карточка
red card|красная карточка
var|вар
offside trap|ловушка офсайда
counterattack|контратака
pressing|прессинг
tiki-taka|тики-така
libero|либеро
sweeper|чистильщик
playmaker|плеймейкер
false nine|ложная девятка
boxer stance|стойка боксёра
jab|джеб
hook punch|хук
uppercut|апперкот
knockout|нокаут
technical knockout|технический нокаут
sparring|спарринг
dojo|додзё
black belt|чёрный пояс
kata|ката
randori|рандори
ippon|иппон
waza-ari|вадза-ари
grand slam|большой шлем
ace serve|эйс
break point|брейк-пойнт
deuce|равные
tie-break|тай-брейк
baseline|задняя линия
volley shot|удар с лёта
smash|смэш
lob|свеча
drop shot|укороченный
topspin|топспин
slice|слайс
caddie|кэдди
eagle golf|игл
    """),
    ("sports", "hard"): pairs("""
keirin|кейрин
omnium|омниум
madison race|медисон
cyclo-cross|циклокросс
bmx racing|bmx-гонка
trial biking|триал
enduro|эндуро
motocross|мотокросс
speedway|спидвей
ice speedway|спидвей на льду
skeleton sled|скелетон-сани
luge track|санная трасса
bobsled run|бобслейная трасса
nordic combined|лыжное двоеборье
skiathlon|скиатлон
mass start|масс-старт
pursuit race|гонка преследования
individual time trial|разделка
criterium|критериум
gravel race|гравийная гонка
ultramarathon|ультрамарафон
ironman|айронмен
hyrox|хайрокс
spartan race|спартан-рейс
obstacle course|полоса препятствий
parkour run|забег паркура
free running|фриран
tricking|трикинг
calisthenics|воркаут
streetlifting|стритлифтинг
strongman|стронгмен
highland games|игры хайленда
caber toss|метание бревна
stone put|толкание камня
sheaf toss|метание снопа
hurling|ёрлинг
shinty|шинти
camogie|камоги
gaelic football|гэльский футбол
australian football|австралийский футбол
    """),
    ("movies", "easy"): pairs("""
Disney|Дисней
Pixar|Пиксар
Marvel|Марвел
DC Comics|Диси
Minions|Миньоны
Despicable Me|Гадкий я
Ice Age|Ледниковый период
Madagascar|Мадагаскар
Kung Fu Panda|Кунг-фу Панда
How to Train Your Dragon|Как приручить дракона
Zootopia|Зверополис
Inside Out|Головоломка
Up|Вверх
Coco|Тайна Коко
Encanto|Энканто
Turning Red|Я краснею
Luca|Лука
Soul|Душа
Brave|Храбрая сердцем
Ratatouille|Рататуй
Wall-E|ВАЛЛ-И
Monsters Inc|Корпорация монстров
The Incredibles|Суперсемейка
Finding Dory|В поисках Дори
Nemo|Немо
Elsa|Эльза
Anna|Анна
Olaf|Олаф
Simba|Симба
Nala|Нала
Woody|Вуди
Buzz Lightyear|Базз Лайтер
Shrek the ogre|Шрек-огр
Donkey|Осёл Шрека
Fiona|Фиона
Puss|Кот в сапогах из Шрека
Dory|Дори
Marlin|Марлин
Woody cowboy|Вуди ковбой
Buzz|Базз
Mike Wazowski|Майк Вазовски
Sulley|Салли
    """),
    ("movies", "medium"): pairs("""
Blade Runner|Бегущий по лезвию
Arrival|Прибытие
Sicario|Сикарио
No Country for Old Men|Старикам тут не место
There Will Be Blood|Нефть
Everything Everywhere|Всё везде и сразу
Poor Things|Бедные-несчастные
The Whale|Кит
Tár|Тар
Anatomy of a Fall|Анатомия падения
Killers of the Flower Moon|Убийцы цветочной луны
The Batman|Бэтмен Мэтт Ривз
John Wick|Джон Уик
House of the Dragon|Дом Дракона
Andor|Андор
The Bear|Медведь
Succession|Наследники
Ted Lasso|Тед Лассо
The White Lotus|Белый лотос
Severance|Разделение
Fallout series|Фоллаут сериал
Shogun|Сёгун
Slow Horses|Медленные лошади
The Menu|Меню
The Banshees|Банши Инишерина
Aftersun|Aftersun
Past Lives|Прошлые жизни
The Zone of Interest|Зона интереса
Society of the Snow|Общество снега
Godzilla Minus One|Годзилла минус один
The Holdovers|Оставленные
American Fiction|Американское чтиво
May December|Май декабрь
All of Us Strangers|Все мы незнакомцы
Anatomy Fall|Анатомия падения фильм
Maestro|Маэстро
Napoleon Ridley|Наполеон Ридли
Ferrari film|Феррари фильм
Wonka|Вонка
Saltburn|Солтберн
Poor Things film|Бедные несчастные фильм
    """),
    ("movies", "hard"): pairs("""
Stalker Tarkovsky|Сталкер Тарковский
Solaris|Солярис
Andrei Rublev|Андрей Рублёв
The Mirror|Зеркало Тарковского
Ivan's Childhood|Иваново детство
Come and See|Иди и смотри
Battleship Potemkin|Броненосец Потёмкин
Man with a Movie Camera|Человек с киноаппаратом
8½|Восемь с половиной
La Dolce Vita|Сладкая жизнь
Bicycle Thieves|Похитители велосипедов
Rome Open City|Рим открытый город
Tokyo Story|Токийская повесть
Rashomon|Расёмон
Seven Samurai|Семь самураев
Ikiru|Жить
Persona Bergman|Персона
Wild Strawberries|Земляничная поляна
The Seventh Seal|Седьмая печать
Fanny and Alexander|Фанни и Александр
Aguirre|Агирре гнев божий
Fitzcarraldo|Фицкарральдо
Wings of Desire|Небо над Берлином
Paris Texas|Париж Техас
Taxi Driver|Таксист
Raging Bull|Бешеный бык
Goodfellas|Славные парни
Casino Scorsese|Казино
The Irishman|Ирландец
Once Upon a Time in America|Однажды в Америке
The Good the Bad and the Ugly|Хороший плохой злой
Once Upon a Time in the West|Однажды на Диком Западе
Mulholland Drive|Малхолланд Драйв
Blue Velvet|Синий бархат
Eraserhead|Голова-ластик
The Elephant Man|Человек-слон
Amour Haneke|Любовь Ханеке
Cache Haneke|Скрытое
The Piano Teacher|Пианистка
Funny Games|Забавные игры
    """),
    ("objects", "easy"): pairs("""
pan|сковородка
lid|крышка
napkin|салфетка
straw|трубочка
corkscrew|штопор
can opener|открывалка
cutting board|доска для нарезки
oven mitt|прихватка
dishwasher|посудомойка
sink|раковина
faucet|кран
shower|душ
bathtub|ванна
toilet|унитаз
toilet paper|туалетная бумага
tissue|салфетка бумажная
trash can|мусорка
recycling bin|контейнер для вторсырья
doorbell|звонок в дверь
doormat|коврик у двери
light switch|выключатель
socket|розетка
extension cord|удлинитель
power bank|повербанк
usb cable|провод юсб
    """),
    ("objects", "medium"): pairs("""
air fryer|аэрогриль
slow cooker|мультиварка
food processor|кухонный комбайн
juicer|соковыжималка
scale kitchen|кухонные весы
timer kitchen|таймер на кухне
rolling pin|скалка
sieve|сито
colander|дуршлаг
grater|тёрка
peeler|овощечистка
tongs|щипцы
ladle|половник
whisk|венчик
spatula|лопатка
    """),
    ("objects", "hard"): pairs("""
astrolabe|астролябия
quadrant|квадрант
octant|октант
theodolite|теодолит
alidade|алидада
clinometer|клинометр
hygrometer|гигрометр
psychrometer|психрометр
manometer|манометр
bolometer|болометр
spectroscope|спектроскоп
interferometer|интерферометр
oscilloscope|осциллограф
galvanometer|гальванометр
ammeter|амперметр
voltmeter|вольтметр
ohmmeter|омметр
rheostat|реостат
potentiometer|потенциометр
inductor coil|катушка индуктивности
capacitor|конденсатор
vacuum tube|радиолампа
cathode ray|электронно-лучевая трубка
slide rule|логарифмическая линейка
planimeter|планиметр
pantograph|пантограф
camera lucida|камера-люцида
magic lantern|волшебный фонарь
zoetrope|зоотроп
phenakistiscope|фенакистископ
stereoscope|стереоскоп
camera obscura|камера-обскура
daguerreotype|дагеротип
cyanotype|цианотипия
letterpress|высокая печать
linotype|линотип
monotype press|монотипия
    """),
    ("nature", "easy"): pairs("""
bush|куст
branch|ветка
root|корень
seed|семя
berry|ягода
cone|шишка еловая
acorn|жёлудь
chestnut|каштан
apple tree|яблоня
cherry tree|вишня дерево
spruce|ель
fir|пихта
linden|липа
poplar|тополь
ash tree|ясень
elm|вяз
willow tree|ива дерево
lily|лилия
daisy|ромашка
chamomile|ромашка аптечная
poppy|мак
violet flower|фиалка
carnation|гвоздика цветок
lilac|сирень
jasmine|жасмин
mint|мята
nettle|крапива
dandelion fluff|одуванчиковый пух
snowflake|снежинка
puddle|лужа
    """),
    ("nature", "medium"): pairs("""
ridge|хребет
saddle mountain|седловина
foothills|предгорье
piedmont|подножие гор
alluvial fan|конус выноса
floodplain|пойма
oxbow|старица
meander|меандр
rapids|пороги
waterfall cascade|каскад
plunge pool|водобойный котёл
cirque|кар
arete|гребень
horn peak|пик-пирамида
hanging valley|висячая долина
tarn|каровое озеро
esker|оз
drumlin|друмлин
kame|кам
outwash|зандр
loess|лёсс
laterite|латерит
bauxite|боксит
    """),
    ("nature", "hard"): pairs("""
inselberg|останец
butte|бютт
mesa|меса
cuesta|куэста
hogback|хогбэк
graben|грабен
horst|горст
rift valley|рифтовая долина
subduction|субдукция
obduction|обдукция
ophiolite|офиолит
flysch|флиш
molasse|моласса
turbidite|турбидит
varve|ленточная глина
tillite|тиллит
dropstone|дропстоун
erratic|эрратический валун
roche moutonnee|бараньи лбы
striae|ледниковые шрамы
cryoturbation|криотурбация
pingo|пинго
palsa|палса
thermokarst|термокарст
alass|алас
naled|налед
aufeis|наледь
polynya|полынья
sastrugi|заструги
firn line|граница фирна
bergschrund|бергшрунд
serac|серак
crevasse|трещина ледника
moulin|мельница ледника
nunatak|нунатак
ablation|абляция
calving|отёл айсбергов
ice shelf|шельфовый ледник
pack ice|паковый лёд
fast ice|припай
lead ice|разводье
    """),
    ("actions", "easy"): pairs("""
come|приходить
go|уходить
enter|входить
leave|уходить вон
return|возвращаться
stay|оставаться
rest|отдыхать
work|работать
start|начинать
stop|останавливаться
continue|продолжать
repeat|повторять
choose|выбирать
decide|решать
think|думать
remember|помнить
forget|забывать
know|знать
memorize|запоминать
teach|учить
explain|объяснять
ask|спрашивать
answer|отвечать
call|звонить
send|отправлять
receive|получать
show|показывать
bring|приносить
put|класть
lift|поднимать
    """),
    ("actions", "medium"): pairs("""
assemble|собирать
disassemble|разбирать
tighten|затягивать
loosen|ослаблять
screw|закручивать
unscrew|откручивать
nail|забивать гвоздь
drill hole|сверлить
saw wood|пилить
sand|шлифовать
paint wall|красить стену
wallpaper|клеить обои
tile|класть плитку
plaster|штукатурить
insulate|утеплять
ventilate|проветривать
humidify|увлажнять
dehumidify|осушивать
filter|фильтровать
distill|дистиллировать
ferment|ферментировать
pickle veg|мариновать
smoke fish|коптить
cure meat|вялить
marinate|мариновать мясо
knead|месить
proof dough|расстаивать тесто
caramelize|карамелизовать
blanch|бланшировать
poach egg|варить яйцо-пашот
    """),
    ("actions", "hard"): pairs("""
transliterate|транслитерировать
transcribe|транскрибировать
calque|калькировать
gloss|глоссировать
lemmatize|лемматизировать
tokenize|токенизировать
parse syntax|парсить синтаксис
compile code|компилировать
link binary|линковать
profile code|профилировать
benchmark|бенчмаркать
fuzz test|фаззить
deadlock|заходить в дедлок
livelock|заходить в лайвлок
debounce|дебаунсить
throttle calls|троттлить
memoize|мемоизировать
currying|каррировать
hoist|поднимать объявление
shim|ставить шим
polyfill|полифиллить
transpile|транспилировать
minify|минифицировать
tree-shake|трясти дерево импортов
hydrate ui|гидратировать интерфейс
annotate|аннотировать
instrument code|инструментировать
canary release|выкатывать канарейку
blue green deploy|сине-зелёный деплой
feature flag|включать фичефлаг
roll back|откатывать релиз
hot patch|ставить хотфикс
ship to prod|выкатывать в прод
page oncall|пейджить дежурного
war room|собирать военную комнату
postmortem|писать постмортем
runbook|открывать ранбук
toil work|делать тойл
sre watch|дежурить sre
incident review|разбор инцидента
    """),
    ("cities", "easy"): pairs("""
Novgorod|Новгород
Pskov|Псков
Smolensk|Смоленск
Tula|Тула
Kaluga|Калуга
Ryazan|Рязань
Vladimir|Владимир
Yaroslavl|Ярославль
Kostroma|Кострома
Vologda|Вологда
Arkhangelsk|Архангельск
Murmansk|Мурманск
Kaliningrad|Калининград
Rostov|Ростов
Krasnodar|Краснодар
Stavropol|Ставрополь
Volgograd|Волгоград
Samara|Самара
Saratov|Саратов
Ufa|Уфа
Perm|Пермь
Yekaterinburg|Екатеринбург
Chelyabinsk|Челябинск
Omsk|Омск
Tomsk|Томск
Krasnoyarsk|Красноярск
Irkutsk|Иркутск
Khabarovsk|Хабаровск
Ukraine|Украина
Belarus|Беларусь
Poland|Польша
Czechia|Чехия
Sweden|Швеция
Norway|Норвегия
Finland|Финляндия
Greece|Греция
Portugal|Португалия
Mexico|Мексика
Argentina|Аргентина
South Korea|Южная Корея
    """),
    ("cities", "medium"): pairs("""
Lyon|Лион
Marseille|Марсель
Nice|Ницца
Bordeaux|Бордо
Toulouse|Тулуза
Cologne|Кёльн
Frankfurt|Франкфурт
Stuttgart|Штутгарт
Dresden|Дрезден
Leipzig|Лейпциг
Rotterdam|Роттердам
The Hague|Гаага
Antwerp|Антверпен
Bruges|Брюгге
Geneva|Женева
Zurich|Цюрих
Basel|Базель
Salzburg|Зальцбург
Innsbruck|Инсбрук
Graz|Грац
Porto|Порту
Seville|Севилья
Valencia|Валенсия
Granada|Гранада
Bilbao|Бильбао
Turin|Турин
Bologna|Болонья
Genoa|Генуя
Palermo|Палермо
Verona|Верона
Glasgow|Глазго
Manchester|Манчестер
Liverpool|Ливерпуль
Birmingham|Бирмингем
Oxford|Оксфорд
Cambridge|Кембридж
Brussels|Брюссель
Krakow square|рынок Кракова
Gdansk port|порт Гданьска
Tallinn old town|старый Таллин
    """),
    ("cities", "hard"): pairs("""
Nuuk|Нуук
Torshavn|Торсхавн
Longyearbyen|Лонгйир
Tromso|Тромсё
Kiruna|Кируна
Rovaniemi|Рованиеми
Inari|Инари
Akureyri|Акюрейри
Thimphu|Тхимпху
Paro|Паро
Male|Мале
Victoria Seychelles|Виктория Сейшелы
Port Louis|Порт-Луи
Antananarivo|Антананариву
Maputo|Мапуту
Gaborone|Габороне
Windhoek|Виндхук
Lusaka|Лусака
Harare|Хараре
Kampala|Кампала
Kigali|Кигали
Bujumbura|Бужумбура
Juba|Джуба
Mogadishu|Могадишо
Djibouti City|Джибути
Asmara|Асмэра
Nouakchott|Нуакшот
Bamako|Бамако
Ouagadougou|Уагадугу
Niamey|Ниамей
N'Djamena|Нджамена
Bangui|Банги
Libreville|Либревиль
Malabo|Малабо
Sao Tome|Сан-Томе
Praia|Прая
Bissau|Бисау
Conakry|Конакри
Freetown|Фритаун
Monrovia|Монровия
    """),
    ("music", "easy"): pairs("""
voice|голос
lyrics|текст песни
verse|куплет
bridge song|бридж
intro|интро
outro|аутро
beat|бит
tempo|темп
volume|громкость
speaker box|колонка
earbuds|вкладыши
studio|студия
rehearsal|репетиция
encore|бис
ovation|овация
stage fright|страх сцены
karaoke night|караоке-вечер
campfire song|песня у костра
national anthem|гимн страны
birthday song|песня на день рождения
wedding march|свадебный марш
fanfare|фанфары
air guitar|воздушная гитара
drumsticks|барабанные палочки
guitar pick|медиатор
capo|каподастр
tuner|тюнер
sheet music|ноты
music stand|пюпитр
choir rehearsal|репетиция хора
poster|постер
autograph|автограф
fan club|фанклуб
tour bus|автобус тура
spotlight singer|софит
clap along|хлопать в такт
humming tune|напевать
whistle tune|мотив свистом
mute button|без звука
music video|клип песни
    """),
    ("music", "medium"): pairs("""
power chord|пауэр-аккорд
barre chord|баррэ
riff|рифф
solo break|брейк соло
backbeat|бэкбит
shuffle rhythm|шаффл
swing feel|свинг
syncopation|синкопа
polyrhythm|полиритмия
ostinato|остинато
modulation|модуляция
key change|смена тональности
relative minor|параллельный минор
circle of fifths|квинтовый круг
cadence|каденция
pedal tone|органный пункт
overtone|обертон
harmonic|флажолет
feedback|фидбэк
distortion|дисторшн
overdrive|овердрайв
fuzz pedal|фазз
wah pedal|вау
delay effect|дилей
reverb|реверб
chorus effect|хорус
flanger|фленжер
phaser|фейзер
compressor|компрессор
equalizer|эквалайзер
limiter|лимитер
drop D|дроп ре
fill drums|заполнение
motif|мотив
theme music|тема
variation|вариация
deceptive cadence|обманная каденция
drone note|бурдон
mix bus|мастер-шина
sidechain|сайдчейн
autotune|автотюн
vocoder|вокодер
    """),
    ("music", "hard"): pairs("""
isorhythm|изоритмия
hocket|хокет
organum|органум
motet|мотет
madrigal|мадригал
canzona|канцона
ricercar|ричеркар
toccata|токката
passacaglia|пассакалия
chaconne|чакона
sarabande|сарабанда
gigue|жига
allemande|аллеманда
courante|куранта
minuet|менуэт
gavotte|гавот
bourree|бурре
siciliana|сицилиана
tarantella|тарантелла
habanera|хабанера
milonga|милонга
fado|фаду
rebetiko|ребетико
klezmer|клезмер
csardas|чардаш
joik|йойк
kulning|кульнинг
morin khuur|морин хуур
qanun|канун
oud|уд
duduk|дудук
zurna|зурна
kamancheh|кеманча
tar lute|тар
ney flute|най
verbunkos|вербункош
sevdah|севдах
tango nuevo|танго нуэво
balafon|балафон
mbira|мбира
kora harp|кора
    """),
    ("people", "easy"): pairs("""
Repin|Репин
Aivazovsky|Айвазовский
Shishkin|Шишкин
Pavarotti|Паваротти
Freddie Mercury|Фредди Меркьюри
John Lennon|Джон Леннон
Paul McCartney|Пол Маккартни
Beyonce|Бейонсе
Rihanna|Рианна
Pele|Пеле
Maradona|Марадона
Zidane|Зидан
Beckham|Бекхэм
Federer|Федерер
Nadal|Надаль
Djokovic|Джокович
Serena Williams|Серена Уильямс
Muhammad Ali|Мохаммед Али
Mike Tyson|Майк Тайсон
Bruce Lee|Брюс Ли
Jackie Chan|Джеки Чан
Ringo Starr|Ринго Старр
George Harrison|Джордж Харрисон
Maria Callas|Мария Каллас
Kanye West|Канье Уэст
Drake|Дрейк
Usain Bolt runner|Усэйн Болт бегун
Cristiano Ronaldo|Криштиану Роналду
Lionel Messi|Лионель Месси
Mark Zuckerberg|Марк Цукерберг
Keanu Reeves|Киану Ривз
Leonardo DiCaprio|Леонардо Ди Каприо
Brad Pitt|Брэд Питт
Angelina Jolie|Анджелина Джоли
Will Smith|Уилл Смит
Tom Cruise|Том Круз
Johnny Depp|Джонни Депп
Robert Downey Jr|Роберт Дауни младший
Scarlett Johansson|Скарлетт Йоханссон
Dwayne Johnson|Дуэйн Джонсон
Chris Hemsworth|Крис Хемсворт
    """),
    ("people", "medium"): pairs("""
Shostakovich composer|Шостакович
Prokofiev composer|Прокофьев
Stravinsky composer|Стравинский
Scriabin|Скрябин
Schnittke|Шнитке
Rostropovich|Ростропович
Richter pianist|Рихтер
Gilels|Гилельс
Oistrakh|Ойстрах
Maya Plisetskaya|Майя Плисецкая
Galina Ulanova|Галина Уланова
Rudolf Nureyev|Рудольф Нуреев
Mikhail Baryshnikov|Михаил Барышников
Tarkovsky|Тарковский
Eisenstein|Эйзенштейн
Parajanov|Параджанов
Mikhalkov|Михалков
Ryazanov|Рязанов
Gaidai|Гайдай
Daneliya|Данелия
Sokurov|Сокуров
Zvyagintsev|Звягинцев
Gergiev|Гергиев
David Oistrakh|Давид Ойстрах
Lungin|Лунгин
Vertov|Вертов
Bondarchuk|Бондарчук
Konchalovsky|Кончаловский
German Aleksei|Герман
Muratova|Муратова
Shepitko|Шепитько
Askoldov|Аскольдов
Klimov|Климов
Abuladze|Абуладзе
Ioseliani|Иоселиани
Gherman|Алексей Герман
Balabanov|Балабанов
Bodrov|Бодров
Bekmambetov|Бекмамбетов
Zvyagintsev Leviathan|Левиафан Звягинцев
Lungin Island|Остров Лунгина
    """),
    ("people", "hard"): pairs("""
Herodotus|Геродот
Thucydides|Фукидид
Tacitus|Тацит
Plutarch|Плутарх
Seneca|Сенека
Cicero|Цицерон
Marcus Aurelius|Марк Аврелий
Augustine|Августин
Aquinas|Фома Аквинский
Erasmus|Эразм
Montaigne|Монтень
Pascal|Паскаль
Spinoza|Спиноза
Leibniz|Лейбниц
Hume|Юм
Kant|Кант
Hegel|Гегель
Schopenhauer|Шопенгауэр
Nietzsche|Ницше
Kierkegaard|Кьеркегор
Heidegger|Хайдеггер
Wittgenstein|Витгенштейн
Arendt|Арендт
Foucault|Фуко
Derrida|Деррида
Deleuze|Делёз
Plotinus|Плотин
Epictetus|Эпиктет
Livy|Тит Ливий
Suetonius|Светоний
Occam|Оккам
Anselm|Ансельм
Abelard|Абеляр
Husserl|Гуссерль
Adorno|Адорно
Benjamin critic|Беньямин
Levinas|Левинас
Ricoeur|Рикёр
Merleau-Ponty|Мерло-Понти
Sartre|Сартр
Camus|Камю
Beauvoir|Бовуар
    """),
    ("harry_potter", "easy"): pairs("""
Muggle|магл
Dudley Dursley|Дадли Дурсль
Petunia Dursley|Петунья Дурсль
Vernon Dursley|Вернон Дурсль
Privet Drive|Тисовая улица
cupboard under the stairs|чулан под лестницей
Hogwarts Express|Хогвартс-экспресс
cauldron|котёл
robe|мантия
pumpkin juice|тыквенный сок
Bertie Bott's Beans|драже Берти Боттс
Hagrid's hut|хижина Хагрида
Fang|Клык
Trevor|Тревор
Percy Weasley|Перси Уизли
Charlie Weasley|Чарли Уизли
Bill Weasley|Билл Уизли
seeker|ловец
keeper|вратарь
chaser|охотник
beater|загонщик
bludger|бладжер
quaffle|квоффл
house points|очки факультета
common room|гостиная факультета
Fat Lady|Толстая Дама
ghost|призрак
goblin|гоблин
unicorn|единорог
centaur|кентавр
giant|великан
Ministry of Magic|Министерство магии
Daily Prophet|Ежедневный пророк
Aunt Marge|тётя Мардж
Quirrell|Квиррелл
Norbert|Норберт
wizard chess|волшебные шахматы
Remembrall|напоминалка
prefect|староста
Headmaster|директор
Transfiguration|трансфигурация
Potions class|зельеварение
Herbology|травология
Defence Against the Dark Arts|защита от Тёмных искусств
House Cup|Кубок школы
    """),
    ("harry_potter", "medium"): pairs("""
Oliver Wood|Оливер Вуд
Seamus Finnigan|Симус Финниган
Dean Thomas|Дин Томас
Lavender Brown|Лаванда Браун
Parvati Patil|Парвати Патил
Padma Patil|Падма Патил
Colin Creevey|Колин Криви
Cornelius Fudge|Корнелиус Фадж
Kingsley Shacklebolt|Кингсли Бруствер
Mundungus Fletcher|Наземникус Флетчер
Three Broomsticks|Три метлы
Hog's Head|Кабанья голова
Honeydukes|Сладкое королевство
Zonko's|Зонко
Weasleys' Wizard Wheezes|Всевозможные волшебные вредилки
Flourish and Blotts|Флориш и Блоттс
Madam Malkin|мадам Малкин
Alohomora|Алохомора
Nox|Нокс
Stupefy|Ступефай
Protego|Протего
Obliviate|Обливиейт
Triwizard champions|чемпионы Турнира
Oliver captain|капитан Вуд
Slytherin team|команда Слизерина
Gryffindor team|команда Гриффиндора
Quidditch World Cup|Чемпионат мира по квиддичу
Veela|вила
Krum's shark|акула Крама
egg clue|яйцо-подсказка
    """),
    ("harry_potter", "hard"): pairs("""
Andromeda Tonks|Андромеда Тонкс
Ted Tonks|Тед Тонкс
Walburga Black|Вальбурга Блэк
Phineas Nigellus|Финеас Найджелус
Helena Ravenclaw|Елена Когтевран
Grey Lady|Серая Дама
Bloody Baron|Кровавый барон
Fat Friar|Толстый монах
Professor Binns|профессор Биннс
Charity Burbage|Чарити Бербидж
Alecto Carrow|Алекто Кэрроу
Amycus Carrow|Амикус Кэрроу
Pius Thicknesse|Пий Толстоватый
Rufus Scrimgeour|Руфус Скримджер
Amelia Bones|Амелия Боунс
Susan Bones|Сьюзен Боунс
Ernie Macmillan|Эрни Макмиллан
Justin Finch-Fletchley|Джастин Финч-Флетчли
Hannah Abbott|Ганна Аббот
Terry Boot|Терри Бут
Michael Corner|Майкл Корнер
Anthony Goldstein|Энтони Голдштейн
Zacharias Smith|Захария Смит
Cormac McLaggen|Кормак Маклагген
Romilda Vane|Ромильда Вейн
Marietta Edgecombe|Мариэтта Эджком
Morfin Gaunt|Морфин Мракс
Merope Gaunt|Меропа Мракс
Marvolo Gaunt|Марволо Мракс
Gaunt shack|лачуга Мраксов
Little Hangleton|Литтл Хэнглтон
Riddle House|дом Реддлов
    """),
    ("transport", None): pairs("""
minibus|микроавтобус
marshrutka|маршрутка
double-decker|двухэтажный автобус
quad bike|квадроцикл
snowmobile|снегоход
hoverboard|гироскутер
unicycle|моноцикл
tandem bike|тандем
rickshaw|рикша
cycle rickshaw|велорикша
camper|кемпер
motorhome|дом на колёсах
pickup truck|пикап
van|фургон
lorry|фура
tow truck|эвакуатор
snowplow|снегоуборщик
garbage truck|мусоровоз
cement mixer|бетономешалка
crane truck|автокран
forklift|погрузчик
bulldozer|бульдозер
excavator|экскаватор
steamroller|каток
combine harvester|комбайн
golf cart|гольф-кар
caravan trailer|автодом-прицеп
tuk tuk auto|авторикша
ice cream truck|машина с мороженым
school bus|школьный автобус
sleeper train|поезд с плацкартом
maglev|маглев
hydrofoil|судно на подводных крыльях
    """),
    ("clothes", None): pairs("""
parka|парка
anorak|анорак
windbreaker|ветровка
down jacket|пуховик
fur coat|шуба
cape|накидка
poncho|пончо
kimono|кимоно
sari|сари
hijab|хиджаб
turban|тюрбан
beret|берет
fedora|федора
panama hat|панама
ushanka|ушанка
mittens|варежки
sash|кушак
suspenders|подтяжки
stockings|чулки
knee socks|гольфы
ankle boots|ботильоны
loafers|лоферы
oxfords|оксфорды
brogues|броги
moccasins|мокасины
flip-flops|сланцы
clogs|сабо
wedges|танкетки
stiletto|шпилька
platform shoes|платформы
earmuffs|наушники меховые
gauntlets|краги
    """),
    ("fairy_tales", None): pairs("""
The Golden Fish|Сказка о золотой рыбке
The Tale of Tsar Saltan|Сказка о царе Салтане
Ruslan and Ludmila|Руслан и Людмила
Ilya Muromets|Илья Муромец
Dobrynya Nikitich|Добрыня Никитич
Alyosha Popovich|Алёша Попович
Nightingale the Robber|Соловей-разбойник
Sadko|Садко
The Frog Princess|Царевна-лягушка
Sister Alyonushka|Сестрица Алёнушка
hut on chicken legs|избушка на курьих ножках
magic tablecloth|скатерть-самобранка
flying carpet|ковёр-самолёт
seven-league boots|сапоги-скороходы
invisibility cap|шапка-невидимка
living water|живая вода
dead water|мёртвая вода
Firebird feather|перо Жар-птицы
The Snow Queen|Снежная королева
Kai and Gerda|Кай и Герда
The Steadfast Tin Soldier|Стойкий оловянный солдатик
The Emperor's New Clothes|Новое платье короля
Jack and the Beanstalk|Джек и бобовый стебель
The Wolf and the Seven Kids|Волк и семеро козлят
Rumpelstiltskin|Румпельштильцхен
The Pied Piper|Гамельнский крысолов
Finist the Bright Falcon|Финист ясный сокол
Marya Morevna|Марья Моревна
The Little Prince|Маленький принц
Svyatogor|Святогор
Koschei's death|смерть Кощея
living and dead water|живая и мёртвая вода
 magician Chernomor|Черномор
Lyudmila|Людмила
Ruslan|Руслан
golden cockerel|золотой петушок
priest Balda|поп и Балда
fisherman and the fish|старик и золотая рыбка
The Nutcracker tale|Щелкунчик сказка
Twelve Months|Двенадцать месяцев
    """),
    ("technology", None): pairs("""
browser|браузер
search engine|поисковик
tab|вкладка
bookmark|закладка
cache|кэш
captcha|капча
vpn|впн
proxy|прокси
firmware|прошивка
ransomware|шифровальщик
malware|вредонос
phishing|фишинг
spam|спам
push notification|пуш
dark mode|тёмная тема
incognito|инкогнито
cloud drive|облачный диск
sync|синхронизация
backup|бэкап
digital signature|электронная подпись
hash|хеш
token|токен
api|апи
sdk|сдк
open source|открытый код
repository|репозиторий
commit|коммит
pull request|пул-реквест
two factor|двухфакторка
restore|восстановление
cookie web|cookie сайта
newsletter|рассылка
    """),
    ("holidays", None): pairs("""
wrapping paper|обёртка
advent calendar|адвент-календарь
carol|колядка
blini week|блины на масленицу
Easter egg|пасхальное яйцо
kulich|кулич
willow Sunday|вербное воскресенье
Knowledge Day|День знаний
first bell|первый звонок
last bell|последний звонок
Halloween costume|костюм на хэллоуин
jack-o-lantern|тыква-фонарь
trick or treat|сладость или гадость
Christmas market|рождественская ярмарка
mulled wine|глинтвейн
gingerbread|пряник
mistletoe|омела
immortal regiment|бессмертный полк
paskha dessert|пасха творожная
New Year address|новогоднее обращение
countdown ball|шар на таймс-сквер
secret Santa|тайный санта
office party|корпоратив
May holidays|майские праздники
Navy Day|день ВМФ
Cosmonautics Day|день космонавтики
Teacher's Day|день учителя
midnight chimes|куранты
Old New Year|Старый Новый год
Christmas Eve|сочельник
Epiphany bathing|крещенские купания
Ivan Kupala|Иван Купала
Russia Day|День России
Unity Day|День народного единства
holiday postcard|праздничная открытка
festive table|праздничный стол
first of September bouquet|букет 1 сентября
Women's Day bouquet|букет на 8 марта
Defender souvenirs|сувениры на 23 февраля
New Year fireworks show|новогодний салют
    """),
    ("school", None): pairs("""
timetable|расписание
homeroom|классный час
form teacher|классный руководитель
head teacher|завуч
detention|оставление после уроков
skip class|прогул
tardy|опоздание
attendance|посещаемость
report card|табель
parent meeting|родительское собрание
open day|день открытых дверей
olympiad school|олимпиада школьная
essay|сочинение
dictation|диктант
oral exam|устный экзамен
cheat sheet|шпаргалка
calculator|калькулятор
compasses|циркуль
highlighter|маркер-выделитель
binder|скоросшиватель
folder|папка
locker|шкафчик
assembly hall|актовый зал
recess|большая перемена
afterschool|продлёнка
tutor|репетитор
presentation|презентация
project|проект
supply teacher|замена учителя
semester|семестр
quarter term|четверть
graduation|выпуск
diploma|диплом
scholarship|стипендия
dormitory|общежитие
campus|кампус
lecture|лекция
seminar|семинар
lab work|лабораторная
dean|декан
faculty|факультет вуза
    """),
    ("space", None): pairs("""
Mercury|Меркурий
Uranus|Уран
Neptune|Нептун
Pluto|Плутон
moon landing|высадка на Луну
Apollo|Аполлон
Soyuz|Союз
Gagarin's flight|полёт Гагарина
ISS|МКС
Baikonur|Байконур
Cape Canaveral|мыс Канаверал
mission control|центр управления
spacewalk|выход в открытый космос
airlock|шлюз
docking|стыковка
reentry|вход в атмосферу
heat shield|теплозащита
rover|марсоход
probe|зонд
lander|посадочный модуль
Hubble|Хаббл
James Webb|Джеймс Уэбб
radio telescope|радиотелескоп
observatory|обсерватория
planetarium|планетарий
astronomical unit|астрономическая единица
parsec|парсек
exoplanet|экзопланета
module|модуль
orbiter|орбитальный аппарат
Kuiper belt|пояс Койпера
Oort cloud|облако Оорта
solar wind|солнечный ветер
sunspot|солнечное пятно
solar flare|солнечная вспышка
red dwarf|красный карлик
white dwarf|белый карлик
neutron star|нейтронная звезда
pulsar|пульсар
magnetar|магнетар
    """),
    ("hobbies", None): pairs("""
paper folding art|складывание фигурок из бумаги
scrapbooking|скрапбукинг
calligraphy|каллиграфия
watercolor|акварель
oil painting|живопись маслом
sketching|скетчинг
still life|натюрморт
plein air|пленэр
pottery wheel|гончарный круг
knitting needles|спицы
crochet hook|крючок
embroidery hoop|пяльцы
cross stitch|вышивка крестом
macrame|макраме
beading|бисероплетение
wood carving|резьба по дереву
pyrography|выжигание
model trains|модели поездов
geocaching|геокешинг
birdwatching|бердвотчинг
urban sketching|городские зарисовки
lettering|леттеринг
acrylics|акрил
glazing clay|глазурь
scale models|масштабные модели
jewelry making|украшения своими руками
foraging|сбор грибов
stamp collecting|коллекционирование марок
coin collecting|коллекционирование монет
dungeon master|мастер подземелий
role playing|ролевые игры
cosplay|косплей
fan fiction|фанфики
home studio recording|домашняя студия
photography walks|фотопрогулки
greenhouse plants|теплица с растениями
bonsai|бонсай
aquarium hobby|аквариумистика
baking bread|выпечка хлеба
home brewing|домашнее пиво
vinyl collecting|коллекция пластинок
    """),
    ("emotions", None): pairs("""
bliss|блаженство
serenity|безмятежность
contentment|удовлетворённость
amusement|забава
mirth|веселье
glee|ликование
elation|приподнятость
enthusiasm|энтузиазм
zeal|рвение
passion|страсть
affection|привязанность
fondness|симпатия
warmth|теплота
longing|тоска
melancholy|меланхолия
sorrow|скорбь
grief|горе
heartbreak|разбитое сердце
regret|сожаление
remorse|раскаяние
dread|ужас
unease|беспокойство
nervousness|нервозность
irritation|раздражение
annoyance|досада
resentment|обида
bitterness|горечь
indifference|равнодушие
exhilaration|окрылённость
yearning|влечение
schadenfreude|злорадство
homesickness|тоска по дому
wanderlust|страсть к странствиям
awe struck|трепет
restlessness|непоседливость
listlessness|вялость
wistfulness|задумчивая грусть
anticipation|предвкушение
relief after fear|облегчение после страха
butterflies in stomach|бабочки в животе
FOMO|страх упустить
    """),
}
