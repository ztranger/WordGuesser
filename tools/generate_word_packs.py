"""Generate bilingual word pack .txt files for WordGuesser."""

from __future__ import annotations

from pathlib import Path

ROOT = Path(r"C:\Projects\Hpg\WordGuesser\app\src\main\assets\words")
RETIRED = [
    "animals.txt",
    "food.txt",
    "professions.txt",
    "sports.txt",
    "movies.txt",
    "objects.txt",
    "nature.txt",
    "actions.txt",
]

EN_HEADER = "# {title}\n# One word per line. Lines starting with # are ignored.\n"
RU_HEADER = "# {title}\n# По одному слову на строку. Строки с # игнорируются.\n"

TITLES = {
    ("animals", "easy"): ("Animals · Easy", "Животные · Простые"),
    ("animals", "medium"): ("Animals · Medium", "Животные · Средние"),
    ("animals", "hard"): ("Animals · Hard", "Животные · Сложные"),
    ("food", "easy"): ("Food · Easy", "Еда · Простые"),
    ("food", "medium"): ("Food · Medium", "Еда · Средние"),
    ("food", "hard"): ("Food · Hard", "Еда · Сложные"),
    ("professions", "easy"): ("Jobs · Easy", "Профессии · Простые"),
    ("professions", "medium"): ("Jobs · Medium", "Профессии · Средние"),
    ("professions", "hard"): ("Jobs · Hard", "Профессии · Сложные"),
    ("sports", "easy"): ("Sports · Easy", "Спорт · Простые"),
    ("sports", "medium"): ("Sports · Medium", "Спорт · Средние"),
    ("sports", "hard"): ("Sports · Hard", "Спорт · Сложные"),
    ("movies", "easy"): ("Movies & TV · Easy", "Кино и сериалы · Простые"),
    ("movies", "medium"): ("Movies & TV · Medium", "Кино и сериалы · Средние"),
    ("movies", "hard"): ("Movies & TV · Hard", "Кино и сериалы · Сложные"),
    ("objects", "easy"): ("Objects · Easy", "Предметы · Простые"),
    ("objects", "medium"): ("Objects · Medium", "Предметы · Средние"),
    ("objects", "hard"): ("Objects · Hard", "Предметы · Сложные"),
    ("nature", "easy"): ("Nature · Easy", "Природа · Простые"),
    ("nature", "medium"): ("Nature · Medium", "Природа · Средние"),
    ("nature", "hard"): ("Nature · Hard", "Природа · Сложные"),
    ("actions", "easy"): ("Actions · Easy", "Действия · Простые"),
    ("actions", "medium"): ("Actions · Medium", "Действия · Средние"),
    ("actions", "hard"): ("Actions · Hard", "Действия · Сложные"),
    ("cities", "easy"): ("Cities & countries · Easy", "Города и страны · Простые"),
    ("cities", "medium"): ("Cities & countries · Medium", "Города и страны · Средние"),
    ("cities", "hard"): ("Cities & countries · Hard", "Города и страны · Сложные"),
    ("music", "easy"): ("Music · Easy", "Музыка · Простые"),
    ("music", "medium"): ("Music · Medium", "Музыка · Средние"),
    ("music", "hard"): ("Music · Hard", "Музыка · Сложные"),
    ("people", "easy"): ("Famous people · Easy", "Знаменитости · Простые"),
    ("people", "medium"): ("Famous people · Medium", "Знаменитости · Средние"),
    ("people", "hard"): ("Famous people · Hard", "Знаменитости · Сложные"),
    ("transport", None): ("Transport", "Транспорт"),
    ("clothes", None): ("Clothes", "Одежда"),
    ("fairy_tales", None): ("Fairy tales", "Сказки"),
    ("technology", None): ("Technology", "Технологии"),
    ("holidays", None): ("Holidays", "Праздники"),
    ("school", None): ("School", "Школа"),
    ("space", None): ("Space", "Космос"),
    ("hobbies", None): ("Hobbies", "Хобби"),
    ("emotions", None): ("Emotions", "Эмоции"),
}


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
            raise SystemExit(f"Duplicate in block: {en} | {ru}")
        seen_en.add(key_en)
        seen_ru.add(key_ru)
        out.append((en, ru))
    return out


PACKS: dict[tuple[str, str | None], list[tuple[str, str]]] = {
    ("animals", "easy"): pairs("""
cat|кот
dog|собака
elephant|слон
tiger|тигр
lion|лев
giraffe|жираф
zebra|зебра
monkey|обезьяна
bear|медведь
wolf|волк
fox|лиса
hare|заяц
squirrel|белка
hedgehog|ёж
panda|панда
koala|коала
kangaroo|кенгуру
crocodile|крокодил
snake|змея
turtle|черепаха
frog|лягушка
shark|акула
dolphin|дельфин
whale|кит
penguin|пингвин
eagle|орёл
owl|сова
parrot|попугай
duck|утка
goose|гусь
chicken|курица
rooster|петух
bee|пчела
butterfly|бабочка
spider|паук
hamster|хомяк
camel|верблюд
mouse|мышь
rabbit|кролик
cow|корова
horse|лошадь
pig|свинья
sheep|овца
goat|коза
catfish|сом
goldfish|золотая рыбка
puppy|щенок
kitten|котёнок
pony|пони
donkey|осёл
turkey|индейка
swan|лебедь
crab|краб
ant|муравей
snail|улитка
fish|рыба
bird|птица
fly|муха
mosquito|комар
ladybug|божья коровка
    """),
    ("animals", "medium"): pairs("""
moose|лось
deer|олень
boar|кабан
badger|барсук
raccoon|енот
lizard|ящерица
octopus|осьминог
jellyfish|медуза
sparrow|воробей
crow|ворона
flamingo|фламинго
peacock|павлин
ostrich|страус
hummingbird|колибри
bat|летучая мышь
wasp|оса
dragonfly|стрекоза
scorpion|скорпион
guinea pig|морская свинка
llama|лама
hippo|бегемот
rhino|носорог
cheetah|гепард
lynx|рысь
seal|тюлень
walrus|морж
otter|выдра
beaver|бобр
chameleon|хамелеон
pelican|пеликан
stork|аист
gorilla|горилла
chimpanzee|шимпанзе
buffalo|буйвол
hyena|гиена
woodpecker|дятел
orca|касатка
starfish|морская звезда
sloth|ленивец
ferret|хорёк
leopard|леопард
panther|пантера
falcon|сокол
heron|цапля
toad|жаба
eel|угорь
ray|скат
lobster|омар
shrimp|креветка
beetle|жук
caterpillar|гусеница
    """),
    ("animals", "hard"): pairs("""
bullfinch|снегирь
titmouse|синица
hammerhead shark|акула-молот
seahorse|морской конёк
mantis|богомол
grasshopper|кузнечик
firefly|светлячок
echidna|ехидна
platypus|утконос
anteater|муравьед
orangutan|орангутан
capybara|капибара
alpaca|альпака
yak|як
jackal|шакал
mongoose|мангуст
mink|норка
iguana|игуана
narwhal|нарвал
axolotl|аксолотль
okapi|окапи
tapir|тапир
lemur|лемур
meerkat|сурикат
wombat|вомбат
pangolin|панголин
armadillo|броненосец
manatee|ламантин
dugong|дюгонь
komodo dragon|комодский варан
anaconda|анаконда
viper|гадюка
cobra|кобра
python|питон
albatross|альбатрос
condor|кондор
kiwi|киви
toucan|тукан
lyrebird|лирохвост
wolverine|росомаха
sable|соболь
marten|куница
chinchilla|шиншилла
porcupine|дикобраз
bison|бизон
ibex|горный козёл
    """),
    ("food", "easy"): pairs("""
pizza|пицца
burger|бургер
sushi|суши
soup|суп
salad|салат
bread|хлеб
cheese|сыр
butter|масло
egg|яйцо
milk|молоко
tea|чай
coffee|кофе
juice|сок
water|вода
ice cream|мороженое
cake|торт
cookie|печенье
candy|конфета
chocolate|шоколад
apple|яблоко
banana|банан
orange|апельсин
pear|груша
grape|виноград
watermelon|арбуз
strawberry|клубника
tomato|помидор
cucumber|огурец
potato|картошка
carrot|морковь
onion|лук
garlic|чеснок
rice|рис
pasta|паста
pancake|блин
dumpling|пельмень
hot dog|хот-дог
french fries|картошка фри
chicken|курица
fish|рыба
meat|мясо
sausage|колбаса
yogurt|йогурт
honey|мёд
jam|варенье
porridge|каша
pie|пирог
sandwich|бутерброд
chips|чипсы
popcorn|попкорн
    """),
    ("food", "medium"): pairs("""
borscht|борщ
shawarma|шаурма
steak|стейк
lasagna|лазанья
risotto|ризотто
ramen|рамен
taco|тако
burrito|бурито
falafel|фалафель
hummus|хумус
croissant|круассан
baguette|багет
bagel|бейгл
donut|пончик
waffle|вафля
tiramisu|тирамису
cheesecake|чизкейк
eclair|эклер
marshmallow|маршмеллоу
omelet|омлет
bacon|бекон
ham|ветчина
caviar|икра
shrimp|креветка
avocado|авокадо
broccoli|брокколи
spinach|шпинат
pumpkin|тыква
eggplant|баклажан
zucchini|кабачок
pineapple|ананас
mango|манго
kiwi|киви
pomegranate|гранат
blueberry|черника
raspberry|малина
peach|персик
apricot|абрикос
lemonade|лимонад
smoothie|смузи
buckwheat|гречка
khachapuri|хачапури
khinkali|хинкали
shashlik|шашлык
syrniki|сырники
okroshka|окрошка
pilaf|плов
nuggets|наггетсы
muffin|маффин
brownie|брауни
    """),
    ("food", "hard"): pairs("""
tom yum|том ям
paella|паэлья
goulash|гуляш
cheburek|чебурек
napoleon cake|наполеон
compote|компот
kefir|кефир
cottage cheese|творог
mussel|мидия
squid|кальмар
asparagus|спаржа
olives|оливки
pickle|соленье
soy sauce|соевый соус
olive oil|оливковое масло
mustard|горчица
mayonnaise|майонез
cinnamon|корица
vanilla|ваниль
ginger|имбирь
marmalade|мармелад
quinoa|киноа
couscous|кускус
tabbouleh|табуле
ratatouille|рататуй
gazpacho|гаспачо
pho|фо
kimchi|кимчи
miso soup|мисо-суп
tempura|темпура
wasabi|васаби
truffle|трюфель
foie gras|фуа-гра
oyster|устрица
anchovy|анчоус
saffron|шафран
cardamom|кардамон
turmeric|куркума
wasabi peas|васаби-горошек
baklava|пахлава
halva|халва
panna cotta|панна котта
creme brulee|крем-брюле
profiterole|профитроль
quiche|киш
fondue|фондю
ceviche|севиче
tartare|тартар
carpaccio|карпаччо
tzatziki|дзадзики
    """),
    ("professions", "easy"): pairs("""
doctor|врач
teacher|учитель
cook|повар
driver|водитель
pilot|пилот
police officer|полицейский
firefighter|пожарный
soldier|солдат
nurse|медсестра
dentist|стоматолог
builder|строитель
painter|маляр
singer|певец
actor|актёр
dancer|танцор
artist|художник
writer|писатель
farmer|фермер
hairdresser|парикмахер
photographer|фотограф
journalist|журналист
waiter|официант
cashier|кассир
salesperson|продавец
postman|почтальон
librarian|библиотекарь
security guard|охранник
sailor|моряк
captain|капитан
baker|пекарь
coach|тренер
judge|судья
lawyer|адвокат
programmer|программист
engineer|инженер
architect|архитектор
scientist|учёный
astronaut|космонавт
clown|клоун
magician|фокусник
king|король
president|президент
nanny|няня
cleaner|уборщик
mechanic|механик
veterinarian|ветеринар
taxi driver|таксист
fisherman|рыбак
hunter|охотник
miner|шахтёр
    """),
    ("professions", "medium"): pairs("""
surgeon|хирург
professor|профессор
designer|дизайнер
electrician|электрик
plumber|сантехник
carpenter|плотник
welder|сварщик
flight attendant|стюардесса
pastry chef|кондитер
bartender|бармен
barista|бариста
gardener|садовник
makeup artist|визажист
translator|переводчик
director|режиссёр
musician|музыкант
sculptor|скульптор
detective|детектив
rescuer|спасатель
biologist|биолог
chemist|химик
physicist|физик
psychologist|психолог
pharmacist|фармацевт
accountant|бухгалтер
banker|банкир
realtor|риелтор
tour guide|гид
blogger|блогер
courier|курьер
secretary|секретарь
diplomat|дипломат
politician|политик
jeweler|ювелир
tailor|портной
blacksmith|кузнец
forester|лесник
composer|композитор
producer|продюсер
animator|аниматор
paramedic|фельдшер
notary|нотариус
mayor|мэр
news anchor|телеведущий
locksmith|слесарь
truck driver|дальнобойщик
masseur|массажист
fitness trainer|фитнес-тренер
volunteer|волонтёр
spy|шпион
    """),
    ("professions", "hard"): pairs("""
anesthesiologist|анестезиолог
cardiologist|кардиолог
ophthalmologist|офтальмолог
dermatologist|дерматолог
pediatrician|педиатр
midwife|акушерка
speech therapist|логопед
dietitian|диетолог
archaeologist|археолог
geologist|геолог
meteorologist|метеоролог
astronomer|астроном
sysadmin|сисадмин
copywriter|копирайтер
recruiter|рекрутер
logistics manager|логист
dispatcher|диспетчер
customs officer|таможенник
border guard|пограничник
concierge|консьерж
choreographer|хореограф
stunt performer|каскадёр
screenwriter|сценарист
cameraman|оператор
game designer|геймдизайнер
watchmaker|часовщик
shoemaker|сапожник
ecologist|эколог
ambassador|посол
archivist|архивариус
sommelier|сомелье
actuary|актуарий
cartographer|картограф
restorer|реставратор
cryptographer|криптограф
orthodontist|ортодонт
pathologist|патологоанатом
oceanographer|океанолог
paleontologist|палеонтолог
seismologist|сейсмолог
etymologist|этимолог
choreologist|балетовед
glassblower|стеклодув
beekeeper|пчеловод
falconer|сокольничий
lighthouse keeper|смотритель маяка
    """),
    ("sports", "easy"): pairs("""
football|футбол
hockey|хоккей
basketball|баскетбол
volleyball|волейбол
tennis|теннис
swimming|плавание
boxing|бокс
running|бег
skiing|лыжи
skating|коньки
chess|шахматы
yoga|йога
cycling|велосипед
golf|гольф
baseball|бейсбол
rugby|регби
badminton|бадминтон
table tennis|настольный теннис
gymnastics|гимнастика
wrestling|борьба
karate|карате
judo|дзюдо
surfing|сёрфинг
skateboard|скейтборд
fishing|рыбалка
dancing|танцы
fitness|фитнес
marathon|марафон
sprint|спринт
jump|прыжок
bowling|боулинг
darts|дартс
billiards|бильярд
horse racing|скачки
snowboarding|сноуборд
figure skating|фигурное катание
weightlifting|тяжёлая атлетика
climbing|скалолазание
parkour|паркур
esports|киберспорт
    """),
    ("sports", "medium"): pairs("""
handball|гандбол
water polo|водное поло
diving|прыжки в воду
rowing|гребля
sailing|парусный спорт
biathlon|биатлон
curling|кёрлинг
fencing|фехтование
archery|стрельба из лука
triathlon|триатлон
high jump|прыжок в высоту
long jump|прыжок в длину
pole vault|прыжок с шестом
shot put|толкание ядра
javelin|метание копья
discus|метание диска
taekwondo|тхэквондо
kickboxing|кикбоксинг
aikido|айкидо
formula 1|формула-1
rally|ралли
karting|картинг
crossfit|кроссфит
pilates|пилатес
aerobics|аэробика
mountaineering|альпинизм
polo|поло
cricket|крикет
squash|сквош
sumo|сумо
paintball|пейнтбол
lacrosse|лакросс
skydiving|прыжки с парашютом
wakeboard|вейкборд
windsurfing|виндсёрфинг
kitesurfing|кайтсёрфинг
short track|шорт-трек
speed skating|конькобежный спорт
freestyle wrestling|вольная борьба
american football|американский футбол
    """),
    ("sports", "hard"): pairs("""
synchronized swimming|синхронное плавание
bobsleigh|бобслей
luge|санный спорт
skeleton|скелетон
ski jumping|прыжки с трамплина
greco-roman wrestling|греко-римская борьба
sambo|самбо
muay thai|муай тай
capoeira|капоэйра
wushu|ушу
kendo|кендо
modern pentathlon|современное пятиборье
orienteering|спортивное ориентирование
floorball|флорбол
netball|нетбол
ultimate frisbee|алтимат
hang gliding|дельтаплан
paragliding|параплан
bungee jumping|банджи-джампинг
decathlon|десятиборье
hammer throw|метание молота
race walking|спортивная ходьба
dressage|выездка
caving|спелеология
powerlifting|пауэрлифтинг
arm wrestling|армрестлинг
kettlebell sport|гиревой спорт
mahjong|маджонг
backgammon|нарды
go|го
snooker|снукер
field hockey|хоккей на траве
water skiing|водные лыжи
spearfishing|подводная охота
jet ski|гидроцикл
freestyle skiing|фристайл
acrobatics|акробатика
trampoline|батут
hurdles|барьерный бег
lasertag|лазертаг
    """),
    ("movies", "easy"): pairs("""
Harry Potter|Гарри Поттер
Star Wars|Звёздные войны
Spider-Man|Человек-паук
Batman|Бэтмен
Superman|Супермен
The Lion King|Король Лев
Frozen|Холодное сердце
Shrek|Шрек
Toy Story|История игрушек
Titanic|Титаник
Home Alone|Один дома
The Avengers|Мстители
Iron Man|Железный человек
Hulk|Халк
Finding Nemo|В поисках Немо
Cars|Тачки
Aladdin|Аладдин
Cinderella|Золушка
Snow White|Белоснежка
Moana|Моана
SpongeBob SquarePants|Губка Боб
The Simpsons|Симпсоны
Friends|Друзья
Tom and Jerry|Том и Джерри
Pirates of the Caribbean|Пираты Карибского моря
Jurassic Park|Парк Юрского периода
King Kong|Кинг-Конг
Godzilla|Годзилла
Joker|Джокер
Thor|Тор
Captain America|Капитан Америка
The Little Mermaid|Русалочка
Beauty and the Beast|Красавица и Чудовище
Mulan|Мулан
Dumbo|Дамбо
Bambi|Бэмби
Transformers|Трансформеры
Jaws|Челюсти
Back to the Future|Назад в будущее
Ghostbusters|Охотники за привидениями
    """),
    ("movies", "medium"): pairs("""
The Lord of the Rings|Властелин колец
The Matrix|Матрица
Inception|Начало
Interstellar|Интерстеллар
Avatar|Аватар
Gladiator|Гладиатор
Forrest Gump|Форрест Гамп
The Godfather|Крёстный отец
Fight Club|Бойцовский клуб
James Bond|Джеймс Бонд
Indiana Jones|Индиана Джонс
Game of Thrones|Игра престолов
Stranger Things|Очень странные дела
The Witcher|Ведьмак
Sherlock|Шерлок
Breaking Bad|Во все тяжкие
The Office|Офис
The Mandalorian|Мандалорец
Black Mirror|Чёрное зеркало
Squid Game|Игра в кальмара
Star Trek|Звёздный путь
Alien|Чужой
The Terminator|Терминатор
The Hobbit|Хоббит
Twilight|Сумерки
The Hunger Games|Голодные игры
It|Оно
The Shining|Сияние
Wonder Woman|Чудо-женщина
Deadpool|Дэдпул
Guardians of the Galaxy|Стражи Галактики
Naruto|Наруто
One Piece|Ван Пис
Spirited Away|Унесённые призраками
Wednesday|Уэнсдей
Barbie|Барби
Oppenheimer|Оппенгеймер
The Dark Knight|Тёмный рыцарь
Pulp Fiction|Криминальное чтиво
Top Gun Maverick|Топ Ган Мэверик
    """),
    ("movies", "hard"): pairs("""
The Shawshank Redemption|Побег из Шоушенка
The Green Mile|Зелёная миля
Dune|Дюна
Mission Impossible|Миссия невыполнима
Fast and Furious|Форсаж
Monsters Inc|Корпорация монстров
How to Train Your Dragon|Как приручить дракона
Tangled|Рапунцель
Bridgerton|Бриджертоны
True Detective|Настоящий детектив
Euphoria|Эйфория
Twin Peaks|Твин Пикс
The X-Files|Секретные материалы
Doctor Who|Доктор Кто
Predator|Хищник
RoboCop|Робокоп
Gremlins|Гремлины
The Chronicles of Narnia|Хроники Нарнии
Divergent|Дивергент
Saw|Пила
Scream|Крик
The Exorcist|Изгоняющий дьявола
Psycho|Психо
Doctor Strange|Доктор Стрэндж
Black Panther|Чёрная пантера
WandaVision|ВандаВижн
Attack on Titan|Атака титанов
Death Note|Тетрадь смерти
Howl's Moving Castle|Ходячий замок Хаула
Princess Mononoke|Принцесса Мононоке
Your Name|Твоё имя
Money Heist|Бумажный дом
The Boys|Пацаны
The Last of Us|Одни из нас
Parasite|Паразиты
Whiplash|Одержимость
La La Land|Ла-Ла Ленд
The Grand Budapest Hotel|Отель Гранд Будапешт
Mad Max Fury Road|Безумный Макс
Inglourious Basterds|Бесславные ублюдки
    """),
    ("objects", "easy"): pairs("""
phone|телефон
computer|компьютер
keyboard|клавиатура
mouse|мышка
lamp|лампа
knife|нож
fork|вилка
spoon|ложка
plate|тарелка
cup|чашка
glass|стакан
chair|стул
table|стол
bed|кровать
pillow|подушка
door|дверь
window|окно
key|ключ
bag|сумка
backpack|рюкзак
umbrella|зонт
watch|часы
mirror|зеркало
toothbrush|зубная щётка
soap|мыло
towel|полотенце
book|книга
pen|ручка
pencil|карандаш
scissors|ножницы
hammer|молоток
ball|мяч
bicycle|велосипед
car|машина
clock|часы настенные
sofa|диван
blanket|одеяло
fridge|холодильник
tv|телевизор
camera|фотоаппарат
hat|шляпа
shoe|ботинок
sock|носок
bottle|бутылка
box|коробка
swing|качели
candle|свеча
broom|веник
bucket|ведро
basket|корзина
mug|кружка
pot|кастрюля
toothpaste|зубная паста
newspaper|газета
magazine|журнал
eraser|ластик
ruler|линейка
notebook|тетрадь
glue|клей
envelope|конверт
map|карта
glasses|очки
doll|кукла
dice|кости
remote control|пульт
thermos|термос
water bottle|бутылка воды
keychain|брелок
    """),
    ("objects", "medium"): pairs("""
tablet|планшет
laptop|ноутбук
headphones|наушники
charger|зарядка
flashlight|фонарик
chandelier|люстра
frying pan|сковорода
kettle|чайник
microwave|микроволновка
oven|духовка
toaster|тостер
blender|блендер
vacuum cleaner|пылесос
washing machine|стиральная машина
iron|утюг
hair dryer|фен
comb|расчёска
shampoo|шампунь
suitcase|чемодан
wallet|кошелёк
curtains|шторы
carpet|ковёр
armchair|кресло
wardrobe|шкаф
shelf|полка
vase|ваза
alarm clock|будильник
nightstand|тумбочка
hanger|вешалка
sponge|губка
mop|швабра
dustpan|совок
matches|спички
lighter|зажигалка
coffee maker|кофеварка
mixer|миксер
speaker|колонка
battery|батарейка
lock|замок
sheet|простыня
painting|картина
thermometer|термометр
calendar|календарь
globe|глобус
binoculars|бинокль
compass|компас
helmet|шлем
tent|палатка
sleeping bag|спальник
kerosene lamp|керосинка
screwdriver|отвёртка
saw|пила
drill|дрель
ladder|лестница
rope|верёвка
puzzle|пазл
playing cards|карты
tripod|штатив
skis|лыжи
ice skates|коньки
racket|ракетка
axe|топор
shovel|лопата
rake|грабли
watering can|лейка
flowerpot|горшок
steering wheel|руль
passport|паспорт
credit card|кредитная карта
piggy bank|копилка
printer|принтер
router|роутер
    """),
    ("objects", "hard"): pairs("""
hourglass|песочные часы
metronome|метроном
abacus|счёты
sextant|секстант
barometer|барометр
anemometer|анемометр
protractor|транспортир
caliper|штангенциркуль
vice|тиски
anvil|наковальня
chisel|стамеска
plane tool|рубанок
thimble|напёрсток
loom|ткацкий станок
spinning wheel|прялка
gramophone|граммофон
typewriter|пишущая машинка
fax machine|факс
pager|пейджер
walkie-talkie|рация
periscope|перископ
kaleidoscope|калейдоскоп
stethoscope|стетоскоп
defibrillator|дефибриллятор
syringe|шприц
scalpel|скальпель
crucible|тигель
retort|колба
bunsen burner|горелка Бунзена
tuning fork|камертон
easel|мольберт
palette|палитра
chalice|кубок
candelabra|канделябр
sundial|солнечные часы
weather vane|флюгер
plumb bob|отвес
spirit level|уровень
mortar|ступка
pestle|пестик
abacus beads|костяшки счётов
inkwell|чернильница
blotter|промокашка
letter opener|нож для бумаги
paperweight|пресс-папье
    """),
    ("nature", "easy"): pairs("""
mountain|гора
volcano|вулкан
hill|холм
forest|лес
field|поле
lake|озеро
river|река
sea|море
ocean|океан
island|остров
beach|пляж
sand|песок
rock|камень
tree|дерево
flower|цветок
grass|трава
leaf|лист
sun|солнце
moon|луна
star|звезда
cloud|облако
rain|дождь
snow|снег
wind|ветер
storm|гроза
rainbow|радуга
fire|огонь
water|вода
ice|лёд
sky|небо
earth|земля
cave|пещера
desert|пустыня
jungle|джунгли
waterfall|водопад
wave|волна
pebble beach|галечный пляж
soil|почва
mud|грязь
fog|туман
oak|дуб
birch|берёза
pine|сосна
rose|роза
tulip|тюльпан
sunflower|подсолнух
mushroom|гриб
icicle|сосулька
dawn|рассвет
sunset|закат
    """),
    ("nature", "medium"): pairs("""
valley|долина
canyon|каньон
oasis|оазис
steppe|степь
tundra|тундра
taiga|тайга
grove|роща
meadow|луг
swamp|болото
pond|пруд
stream|ручей
bay|залив
strait|пролив
peninsula|полуостров
reef|риф
dune|дюна
cliff|утёс
pebble|галька
clay|глина
lava|лава
glacier|ледник
geyser|гейзер
spring|родник
delta|дельта
fjord|фьорд
plateau|плато
crater|кратер
aurora|северное сияние
eclipse|затмение
comet|комета
meteor|метеор
hail|град
dew|роса
frost|иней
thunder|гром
lightning|молния
horizon|горизонт
tide|прилив
current|течение
coral|коралл
iceberg|айсберг
hurricane|ураган
tornado|торнадо
earthquake|землетрясение
avalanche|лавина
oak grove|дубрава
maple|клён
willow|ива
cactus|кактус
moss|мох
dandelion|одуванчик
lily of the valley|ландыш
seashell|ракушка
pinecone|шишка
anthill|муравейник
nest|гнездо
blizzard|метель
drought|засуха
flood|наводнение
    """),
    ("nature", "hard"): pairs("""
gorge|ущелье
grotto|грот
plain|равнина
archipelago|архипелаг
atoll|атолл
boulder|валун
silt|ил
magma|магма
moraine|морена
firn|фирн
karst|карст
stalactite|сталактит
stalagmite|сталагмит
sinkhole|карстовая воронка
estuary|эстуарий
lagoon|лагуна
mangrove|мангры
savanna|саванна
prairie|прерия
chaparral|чапараль
taiga moss|ягель
permafrost|вечная мерзлота
caldera|кальдера
fumarole|фумарола
solfatara|сольфатара
tsunami|цунами
whirlpool|водоворот
monsoon|муссон
sirocco|сирокко
mistral|мистраль
chinook|чинук
penumbra|полутень
equinox|равноденствие
solstice|солнцестояние
zenith|зенит
nadir|надир
biosphere|биосфера
ecosystem|экосистема
plankton|планктон
humus|гумус
    """),
    ("actions", "easy"): pairs("""
run|бегать
walk|ходить
jump|прыгать
sit|сидеть
stand|стоять
sleep|спать
eat|есть
drink|пить
cook|готовить
wash|мыть
open|открывать
close|закрывать
read|читать
write|писать
draw|рисовать
sing|петь
dance|танцевать
play|играть
swim|плавать
fly|летать
drive|водить
laugh|смеяться
cry|плакать
smile|улыбаться
talk|говорить
listen|слушать
look|смотреть
search|искать
find|находить
hide|прятаться
throw|бросать
catch|ловить
push|толкать
pull|тянуть
give|давать
take|брать
buy|покупать
sell|продавать
wait|ждать
help|помогать
hop|подпрыгивать
fall|падать
cut|резать
carry|нести
build|строить
break|ломать
repair|чинить
count|считать
study|учиться
win|побеждать
lose|проигрывать
    """),
    ("actions", "medium"): pairs("""
climb|лезть
crawl|ползать
yawn|зевать
stretch|потягиваться
wake up|просыпаться
get dressed|одеваться
tie shoelaces|завязывать шнурки
fry|жарить
boil|варить
bake|печь
stir|мешать
chew|жевать
swallow|глотать
vacuum|пылесосить
sweep|подметать
iron|гладить
fold|складывать
lock|запирать
whisper|шептать
shout|кричать
argue|спорить
apologize|извиняться
promise|обещать
celebrate|праздновать
hug|обнимать
wave|махать
point|указывать
knock|стучать
ring|звонить
type|печатать
scroll|листать
zoom|приближать
record|записывать
upload|загружать
download|скачивать
charge|заряжать
plug in|включать в розетку
unpack|распаковывать
wrap|заворачивать
measure|измерять
brush teeth|чистить зубы
comb hair|причёсываться
wash dishes|мыть посуду
stand up|вставать
sit down|садиться
lie down|ложиться
take a photo|фотографировать
drive a car|водить машину
walk the dog|выгуливать собаку
plant a tree|сажать дерево
shake hands|пожимать руку
set an alarm|ставить будильник
sneeze|чихать
cough|кашлять
    """),
    ("actions", "hard"): pairs("""
doze|дремать
undress|раздеваться
add salt|солить
taste|пробовать на вкус
set the table|накрывать на стол
do laundry|стирать бельё
negotiate|вести переговоры
improvise|импровизировать
procrastinate|прокрастинировать
meditate|медитировать
hypnotize|гипнотизировать
eavesdrop|подслушивать
decipher|расшифровывать
calibrate|калибровать
improvise dance|экспромтом танцевать
moonwalk|лунная походка
juggle|жонглировать
yodel|йодль
whistle|свистеть
hum|напевать
sketch|набрасывать
doodle|рисовать каракули
brainstorm|мозговой штурм
proofread|вычитывать
translate|переводить
subtitling|субтитровать
debug|отлаживать
refactor|рефакторить
audit|аудировать
filibuster|затягивать речь
sabotage|саботировать
boycott|бойкотировать
campaign|кампанить
lobby|лоббировать
arbitrage|арбитраж
whittle|вырезать из дерева
solder|паять
weld|сваривать
gild|золотить
emboss|тиснить
    """),
    ("cities", "easy"): pairs("""
Moscow|Москва
Saint Petersburg|Санкт-Петербург
London|Лондон
Paris|Париж
Rome|Рим
Berlin|Берлин
Madrid|Мадрид
New York|Нью-Йорк
Los Angeles|Лос-Анджелес
Tokyo|Токио
Beijing|Пекин
Shanghai|Шанхай
Dubai|Дубай
Istanbul|Стамбул
Cairo|Каир
Sydney|Сидней
Rio de Janeiro|Рио-де-Жанейро
Mexico City|Мехико
Toronto|Торонто
Amsterdam|Амстердам
Vienna|Вена
Prague|Прага
Warsaw|Варшава
Kiev|Киев
Minsk|Минск
Russia|Россия
USA|США
China|Китай
Japan|Япония
France|Франция
Germany|Германия
Italy|Италия
Spain|Испания
England|Англия
Brazil|Бразилия
India|Индия
Canada|Канада
Australia|Австралия
Turkey|Турция
Egypt|Египет
    """),
    ("cities", "medium"): pairs("""
Barcelona|Барселона
Lisbon|Лиссабон
Athens|Афины
Stockholm|Стокгольм
Oslo|Осло
Helsinki|Хельсинки
Copenhagen|Копенгаген
Budapest|Будапешт
Bucharest|Бухарест
Sofia|София
Belgrade|Белград
Zagreb|Загреб
Dublin|Дублин
Edinburgh|Эдинбург
Venice|Венеция
Florence|Флоренция
Milan|Милан
Naples|Неаполь
Munich|Мюнхен
Hamburg|Гамбург
Krakow|Краков
Gdansk|Гданьск
Kazan|Казань
Sochi|Сочи
Novosibirsk|Новосибирск
Vladivostok|Владивосток
Seoul|Сеул
Bangkok|Бангкок
Singapore|Сингапур
Hong Kong|Гонконг
Mumbai|Мумбаи
Delhi|Дели
Cape Town|Кейптаун
Nairobi|Найроби
Buenos Aires|Буэнос-Айрес
Lima|Лима
Santiago|Сантьяго
Montreal|Монреаль
Vancouver|Ванкувер
San Francisco|Сан-Франциско
    """),
    ("cities", "hard"): pairs("""
Reykjavik|Рейкьявик
Tallinn|Таллин
Riga|Рига
Vilnius|Вильнюс
Ljubljana|Любляна
Bratislava|Братислава
Luxembourg|Люксембург
Valletta|Валлетта
Tirana|Тирана
Podgorica|Подгорица
Skopje|Скопье
Chisinau|Кишинёв
Yerevan|Ереван
Tbilisi|Тбилиси
Baku|Баку
Astana|Астана
Tashkent|Ташкент
Bishkek|Бишкек
Dushanbe|Душанбе
Ashgabat|Ашхабад
Ulaanbaatar|Улан-Батор
Kathmandu|Катманду
Colombo|Коломбо
Dhaka|Дакка
Yangon|Янгон
Phnom Penh|Пномпень
Vientiane|Вьентьян
Hanoi|Ханой
Jakarta|Джакарта
Manila|Манила
Wellington|Веллингтон
Addis Ababa|Аддис-Абеба
Casablanca|Касабланка
Marrakesh|Марракеш
Tunis|Тунис
Algiers|Алжир
Caracas|Каракас
Bogota|Богота
Quito|Кито
La Paz|Ла-Пас
    """),
    ("music", "easy"): pairs("""
song|песня
singer|певец
guitar|гитара
piano|пианино
drum|барабан
violin|скрипка
microphone|микрофон
concert|концерт
karaoke|караоке
radio|радио
headphones|наушники
playlist|плейлист
hit|хит
chorus|припев
melody|мелодия
rhythm|ритм
note|нота
album|альбом
clip|клип
dance|танец
rock|рок
pop|поп
rap|рэп
jazz|джаз
classical music|классика
lullaby|колыбельная
anthem|гимн
DJ|диджей
orchestra|оркестр
choir|хор
flute|флейта
trumpet|труба
accordion|аккордеон
harmonica|губная гармошка
The Beatles|Битлз
Michael Jackson|Майкл Джексон
Elvis Presley|Элвис Пресли
Mozart|Моцарт
Beethoven|Бетховен
Tchaikovsky|Чайковский
    """),
    ("music", "medium"): pairs("""
saxophone|саксофон
cello|виолончель
harp|арфа
ukulele|укулеле
synthesizer|синтезатор
bass guitar|бас-гитара
conductor|дирижёр
composer|композитор
opera|опера
ballet|балет
symphony|симфония
sonata|соната
duet|дуэт
solo|соло
remix|ремикс
cover|кавер
festival|фестиваль
tour|турне
stage|сцена
spotlight|прожектор
backstage|закулисье
vinyl|виниловая пластинка
cassette|кассета
mp3|мп3
blues|блюз
reggae|регги
metal|метал
disco|диско
techno|техно
folk|фольклор
chanson|шансон
Queen|Квин
Madonna|Мадонна
Eminem|Эминем
Shakira|Шакира
Adele|Адель
Ed Sheeran|Эд Ширан
Taylor Swift|Тейлор Свифт
Billie Eilish|Билли Айлиш
BTS|БТС
    """),
    ("music", "hard"): pairs("""
oboe|гобой
bassoon|фагот
viola|альт
double bass|контрабас
harpsichord|клавесин
theremin|терменвокс
bagpipes|волынка
sitar|ситар
didgeridoo|диджериду
balalaika|балалайка
domra|домра
gusli|гусли
counterpoint|контрапункт
fugue|фуга
arpeggio|арпеджио
glissando|глиссандо
vibrato|вибрато
staccato|стаккато
legato|легато
cadenza|каденция
libretto|либретто
overture|увертюра
nocturne|ноктюрн
etude|этюд
prelude|прелюдия
rhapsody|рапсодия
oratorio|оратория
a cappella|а капелла
polyphony|полифония
atonal|атональная музыка
serialism|сериализм
gregorian chant|григорианский хорал
Vivaldi|Вивальди
Bach|Бах
Chopin|Шопен
Debussy|Дебюсси
Stravinsky|Стравинский
Shostakovich|Шостакович
Prokofiev|Прокофьев
Rachmaninoff|Рахманинов
    """),
    ("people", "easy"): pairs("""
Pushkin|Пушкин
Einstein|Эйнштейн
Newton|Ньютон
Shakespeare|Шекспир
Napoleon|Наполеон
Cleopatra|Клеопатра
Mozart|Моцарт
Picasso|Пикассо
Messi|Месси
Ronaldo|Роналду
Gagarin|Гагарин
Armstrong|Армстронг
Chaplin|Чаплин
Marilyn Monroe|Мэрилин Монро
Putin|Путин
Obama|Обама
Harry Potter|Гарри Поттер
Sherlock Holmes|Шерлок Холмс
Santa Claus|Дед Мороз
Cinderella|Золушка
Superman|Супермен
Spider-Man|Человек-паук
Batman|Бэтмен
Mickey Mouse|Микки Маус
Winnie the Pooh|Винни-Пух
Cheburashka|Чебурашка
Gena the Crocodile|Крокодил Гена
Kolobok|Колобок
Ivan the Terrible|Иван Грозный
Peter the Great|Пётр Первый
Lomonosov|Ломоносов
Mendeleev|Менделеев
Tchaikovsky|Чайковский
Da Vinci|Да Винчи
Van Gogh|Ван Гог
Steve Jobs|Стив Джобс
Bill Gates|Билл Гейтс
Elon Musk|Илон Маск
Cristiano|Криштиану
Usain Bolt|Усэйн Болт
    """),
    ("people", "medium"): pairs("""
Tolstoy|Толстой
Dostoevsky|Достоевский
Chekhov|Чехов
Gogol|Гоголь
Yesenin|Есенин
Mayakovsky|Маяковский
Tsvetaeva|Цветаева
Akhmatova|Ахматова
Gorky|Горький
Bulgakov|Булгаков
Darwin|Дарвин
Tesla|Тесла
Edison|Эдисон
Curie|Кюри
Hawking|Хокинг
Galileo|Галилей
Copernicus|Коперник
Churchill|Черчилль
Gandhi|Ганди
Mandela|Мандела
Lincoln|Линкольн
Washington|Вашингтон
Kennedy|Кеннеди
Martin Luther King|Мартин Лютер Кинг
Mother Teresa|Мать Тереза
Joan of Arc|Жанна д'Арк
Robin Hood|Робин Гуд
King Arthur|король Артур
Merlin|Мерлин
Dracula|Дракула
Frankenstein|Франкенштейн
Darth Vader|Дарт Вейдер
Yoda|Йода
Hermione|Гермиона
Frodo|Фродо
Gandalf|Гэндальф
Wonder Woman|Чудо-женщина
Black Widow|Чёрная вдова
Iron Man|Железный человек
    """),
    ("people", "hard"): pairs("""
Mandelstam|Мандельштам
Brodsky|Бродский
Pasternak|Пастернак
Solzhenitsyn|Солженицын
Nabokov|Набоков
Turgenev|Тургенев
Goncharov|Гончаров
Leskov|Лесков
Kuprin|Куприн
Bunin|Бунин
Faraday|Фарадей
Maxwell|Максвелл
Bohr|Бор
Planck|Планк
Feynman|Фейнман
Mendel|Мендель
Pavlov|Павлов
Sechenov|Сеченов
Vernadsky|Вернадский
Korolev|Королёв
Tsiolkovsky|Циолковский
Kurchatov|Курчатов
Sakharov|Сахаров
Landau|Ландау
Kapitsa|Капица
Machiavelli|Макиавелли
Confucius|Конфуций
Socrates|Сократ
Aristotle|Аристотель
Plato|Платон
Hypatia|Гипатия
Hatshepsut|Хатшепсут
Ashurbanipal|Ашшурбанапал
Hammurabi|Хаммурапи
Gilgamesh|Гильгамеш
Scheherazade|Шехерезада
Rumi|Руми
Omar Khayyam|Омар Хайям
Avicenna|Авиценна
    """),
    ("transport", None): pairs("""
car|машина
bus|автобус
tram|трамвай
trolleybus|троллейбус
metro|метро
train|поезд
airplane|самолёт
helicopter|вертолёт
ship|корабль
boat|лодка
bicycle|велосипед
motorcycle|мотоцикл
scooter|самокат
taxi|такси
truck|грузовик
ambulance|скорая помощь
fire truck|пожарная машина
police car|полицейская машина
tractor|трактор
tank|танк
submarine|подводная лодка
rocket|ракета
hot air balloon|воздушный шар
yacht|яхта
ferry|паром
cable car|канатка
escalator|эскалатор
elevator|лифт
skateboard|скейтборд
roller skates|ролики
sleigh|сани
carriage|карета
wagon|повозка
hovercraft|судно на воздушной подушке
segway|сигвей
monorail|монорельс
funicular|фуникулёр
icebreaker|ледокол
tanker|танкер
barge|баржа
canoe|каноэ
kayak|каяк
raft|плот
glider|планёр
hang glider|дельтаплан
paraglider|параплан
spaceship|космический корабль
space shuttle|шаттл
drone|дрон
tuk-tuk|тук-тук
    """),
    ("clothes", None): pairs("""
t-shirt|футболка
shirt|рубашка
sweater|свитер
hoodie|худи
jacket|куртка
coat|пальто
jeans|джинсы
trousers|брюки
shorts|шорты
skirt|юбка
dress|платье
suit|костюм
tie|галстук
scarf|шарф
hat|шляпа
cap|кепка
beanie|шапка
gloves|перчатки
socks|носки
shoes|туфли
sneakers|кроссовки
boots|сапоги
sandals|сандалии
slippers|тапочки
belt|ремень
underwear|бельё
pajamas|пижама
swimsuit|купальник
raincoat|дождевик
umbrella|зонт
backpack|рюкзак
handbag|сумка
glasses|очки
sunglasses|солнцезащитные очки
watch|часы
bracelet|браслет
necklace|ожерелье
earrings|серьги
ring|кольцо
crown|корона
helmet|шлем
uniform|форма
apron|фартук
overalls|комбинезон
vest|жилет
cardigan|кардиган
turtleneck|водолазка
leggings|леггинсы
tights|колготки
bow tie|бабочка
    """),
    ("fairy_tales", None): pairs("""
Kolobok|Колобок
Turnip|Репка
Teremok|Теремок
Ryaba the Hen|Курочка Ряба
Geese-Swans|Гуси-лебеди
Morozko|Морозко
The Snow Maiden|Снегурочка
Vasilisa the Beautiful|Василиса Прекрасная
Ivan Tsarevich|Иван-царевич
Koschei the Deathless|Кощей Бессмертный
Baba Yaga|Баба-яга
Firebird|Жар-птица
The Little Humpbacked Horse|Конёк-Горбунок
Cinderella|Золушка
Snow White|Белоснежка
Sleeping Beauty|Спящая красавица
Little Red Riding Hood|Красная Шапочка
The Three Little Pigs|Три поросёнка
Hansel and Gretel|Гензель и Гретель
Rapunzel|Рапунцель
The Ugly Duckling|Гадкий утёнок
The Little Mermaid|Русалочка
Thumbelina|Дюймовочка
The Princess and the Pea|Принцесса на горошине
Puss in Boots|Кот в сапогах
Aladdin|Аладдин
Ali Baba|Али-Баба
Sinbad|Синдбад
Pinocchio|Пиноккио
Peter Pan|Питер Пэн
Alice in Wonderland|Алиса в Стране чудес
Wizard of Oz|Волшебник Изумрудного города
Winnie the Pooh|Винни-Пух
Carlson|Карлсон
Cheburashka|Чебурашка
Gena the Crocodile|Крокодил Гена
Shapoklyak|Шапокляк
Dunno|Незнайка
Old Man Hottabych|Старик Хоттабыч
The Bremen Town Musicians|Бременские музыканты
    """),
    ("technology", None): pairs("""
smartphone|смартфон
internet|интернет
Wi-Fi|вай-фай
password|пароль
email|электронная почта
website|сайт
app|приложение
robot|робот
drone|дрон
artificial intelligence|искусственный интеллект
chatbot|чат-бот
QR code|QR-код
USB|юсб
Bluetooth|блютуз
cloud|облако
server|сервер
database|база данных
algorithm|алгоритм
bug|баг
update|обновление
download|скачивание
upload|загрузка
screenshot|скриншот
emoji|эмодзи
hashtag|хештег
selfie|селфи
livestream|прямой эфир
podcast|подкаст
virtual reality|виртуальная реальность
augmented reality|дополненная реальность
3D printer|3D-принтер
smartwatch|умные часы
smart home|умный дом
satellite|спутник
GPS|джипиэс
processor|процессор
graphics card|видеокарта
hard drive|жёсткий диск
SSD|ssd
RAM|оперативная память
firewall|файрвол
antivirus|антивирус
encryption|шифрование
blockchain|блокчейн
bitcoin|биткоин
neural network|нейросеть
machine learning|машинное обучение
quantum computer|квантовый компьютер
nanotechnology|нанотехнологии
biometrics|биометрия
    """),
    ("holidays", None): pairs("""
New Year|Новый год
Christmas|Рождество
Birthday|день рождения
Halloween|Хэллоуин
Easter|Пасха
Valentine's Day|день святого Валентина
March 8|8 Марта
February 23|23 Февраля
Victory Day|День Победы
Maslenitsa|Масленица
Halloween pumpkin|тыква на Хэллоуин
firework|фейерверк
gift|подарок
cake|торт
candle|свеча
balloon|воздушный шар
garland|гирлянда
Christmas tree|ёлка
Santa Claus|Дед Мороз
Snow Maiden|Снегурочка
reindeer|олень
stocking|носок для подарков
carnival|карнавал
masquerade|маскарад
costume|костюм
parade|парад
toast|тост
champagne|шампанское
olivier salad|оливье
tangerines|мандарины
sparkler|бенгальский огонь
snowball fight|снежки
ice rink|каток
wedding|свадьба
engagement|помолвка
anniversary|годовщина
graduation|выпускной
prom|выпускной бал
April Fools|1 апреля
Thanksgiving|День благодарения
    """),
    ("school", None): pairs("""
school|школа
teacher|учитель
student|ученик
lesson|урок
homework|домашка
exam|экзамен
test|контрольная
grade|оценка
diary|дневник
backpack|рюкзак
textbook|учебник
notebook|тетрадь
pen|ручка
pencil|карандаш
eraser|ластик
ruler|линейка
blackboard|доска
chalk|мел
desk|парта
bell|звонок
break|перемена
canteen|столовая
gym|спортзал
library|библиотека
principal|директор
class|класс
classmate|одноклассник
uniform|форма
algebra|алгебра
geometry|геометрия
physics|физика
chemistry|химия
biology|биология
history|история
geography|география
literature|литература
English|английский
PE|физкультура
art|изо
music|музыка
    """),
    ("space", None): pairs("""
Sun|Солнце
Moon|Луна
Earth|Земля
Mars|Марс
Venus|Венера
Jupiter|Юпитер
Saturn|Сатурн
star|звезда
planet|планета
comet|комета
asteroid|астероид
galaxy|галактика
Milky Way|Млечный Путь
black hole|чёрная дыра
astronaut|космонавт
spaceship|космический корабль
rocket|ракета
satellite|спутник
space station|космическая станция
spacesuit|скафандр
telescope|телескоп
orbit|орбита
gravity|гравитация
eclipse|затмение
meteorite|метеорит
constellation|созвездие
Big Dipper|Большая Медведица
North Star|Полярная звезда
alien|инопланетянин
UFO|НЛО
launch|запуск
countdown|обратный отсчёт
weightlessness|невесомость
crater|кратер
atmosphere|атмосфера
vacuum|вакуум
light-year|световой год
nebula|туманность
supernova|сверхновая
quasar|квазар
    """),
    ("hobbies", None): pairs("""
drawing|рисование
painting|живопись
photography|фотография
reading|чтение
writing|писание
cooking|готовка
baking|выпечка
gardening|садоводство
fishing|рыбалка
hiking|походы
camping|кемпинг
collecting stamps|филателия
collecting coins|нумизматика
knitting|вязание
embroidery|вышивка
origami|оригами
modeling|моделизм
chess|шахматы
puzzles|пазлы
crosswords|кроссворды
video games|видеоигры
board games|настолки
Lego|лего
singing|пение
playing guitar|игра на гитаре
dancing|танцы
yoga|йога
running|бег
cycling|велосипед
swimming|плавание
skiing|лыжи
skating|коньки
blogging|блог
vlogging|влог
podcasting|подкасты
travel|путешествия
languages|языки
magic tricks|фокусы
juggling|жонглирование
astronomy|астрономия
    """),
    ("emotions", None): pairs("""
joy|радость
happiness|счастье
sadness|грусть
anger|злость
fear|страх
surprise|удивление
love|любовь
hate|ненависть
envy|зависть
jealousy|ревность
pride|гордость
shame|стыд
guilt|вина
hope|надежда
despair|отчаяние
calm|спокойствие
anxiety|тревога
boredom|скука
excitement|волнение
curiosity|любопытство
nostalgia|ностальгия
loneliness|одиночество
gratitude|благодарность
disappointment|разочарование
relief|облегчение
admiration|восхищение
contempt|презрение
pity|жалость
embarrassment|смущение
confidence|уверенность
insecurity|неуверенность
inspiration|вдохновение
apathy|апатия
euphoria|эйфория
panic|паника
rage|ярость
tenderness|нежность
compassion|сострадание
awe|благоговение
delight|восторг
    """),
}


def write_pack(topic: str, difficulty: str | None, words: list[tuple[str, str]]) -> None:
    key = (topic, difficulty)
    en_title, ru_title = TITLES[key]
    name = f"{topic}_{difficulty}.txt" if difficulty else f"{topic}.txt"
    en_path = ROOT / "en" / name
    ru_path = ROOT / "ru" / name
    en_body = EN_HEADER.format(title=en_title) + "\n".join(en for en, _ in words) + "\n"
    ru_body = RU_HEADER.format(title=ru_title) + "\n".join(ru for _, ru in words) + "\n"
    en_path.write_text(en_body, encoding="utf-8")
    ru_path.write_text(ru_body, encoding="utf-8")
    print(f"{name:28} en={len(words):3} ru={len(words):3}")


def main() -> None:
    ROOT.joinpath("en").mkdir(parents=True, exist_ok=True)
    ROOT.joinpath("ru").mkdir(parents=True, exist_ok=True)
    expected = set(TITLES)
    actual = set(PACKS)
    if expected != actual:
        missing = expected - actual
        extra = actual - expected
        raise SystemExit(f"Pack mismatch missing={missing} extra={extra}")
    for key, words in PACKS.items():
        if len(words) < 35:
            raise SystemExit(f"Too few words in {key}: {len(words)}")
        write_pack(*key, words)
    for lang in ("en", "ru"):
        for name in RETIRED:
            path = ROOT / lang / name
            if path.exists():
                path.unlink()
                print("removed", path)
    print("packs", len(PACKS))


if __name__ == "__main__":
    main()

