var gAdsWidth;
var gAdsHeight;
var global_is_show_popup_ads = false;
var global_is_show_popup_games = false;
var global_pop_up_span = 240000;//240000;//420000;//600000

var global_ads_count_down = 10;
var adBreak;

var recommendedGamesController = (function () {

    function getAllGamges() {

        var typePuzzle = "Puzzle";
        var typeMatch3 = "Match,Three,3";
        var typeRacing = "Racing";
        var typeCard = "Card";
        var typeWord = "Word";
        var global_all_xp_games = [

            ["games/spider/index.html", "img/Spider-Solitaire.jpg", "Spider Solitaire", " <i class='icon-mobile-phone'></i> ", " <i class='icon-tablet'></i> ", " <i class='icon-desktop'></i> ", "image", "Spider,Solitaire,Windows,Cards", typeCard],
            ["games/klondike/index.html", "img/Klondike-Solitaire.jpg", "Klondike Solitaire", " <i class='icon-mobile-phone'></i> ", " <i class='icon-tablet'></i> ", " <i class='icon-desktop'></i> ", "image", "Klondike,Solitaire,Windows,Cards", typeCard],
            ["games/pyramid/index.html", "img/Pyramid-Solitaire.jpg", "Pyramid Solitaire", " <i class='icon-mobile-phone'></i> ", " <i class='icon-tablet'></i> ", " <i class='icon-desktop'></i> ", "image", "Pyramid,Solitaire,Windows,Cards", typeCard],
            ["games/tripeaks/index.html", "img/TriPeaks-Solitaire.jpg", "TriPeaks Solitaire", " <i class='icon-mobile-phone'></i> ", " <i class='icon-tablet'></i> ", " <i class='icon-desktop'></i> ", "image", "TriPeaks,Tri-Peaks,Solitaire,Windows,Cards", typeCard],
            ["games/freecell/index.html", "img/FreeCell-Solitaire.jpg", "FreeCell Solitaire", " <i class='icon-mobile-phone'></i> ", " <i class='icon-tablet'></i> ", " <i class='icon-desktop'></i> ", "image", "FreeCell,Solitaire,Windows,Cards", typeCard],


            ["games/mahjong/index.html", "img/Mahjong-Solitaire.jpg", "Mahjong Solitaire", " <i class='icon-mobile-phone'></i> ", " <i class='icon-tablet'></i> ", " <i class='icon-desktop'></i> ", "image", "Mahjong,Solitaire,Windows,Cards", typeCard],

            ["games/hearts/index.html", "img/Hearts.jpg", "Hearts", " <i class='icon-mobile-phone'></i> ", " <i class='icon-tablet'></i> ", " <i class='icon-desktop'></i> ", "image", "Hearts,Cards", typeCard],
            ["games/spades/index.html", "img/Spades.jpg", "Spades", " <i class='icon-mobile-phone'></i>", " <i class='icon-tablet'></i> ", " <i class='icon-desktop'></i> ", "image", "Spades,Cards", typeCard],
            ["games/crazyeights/index.html", "img/Crazy-Eights.jpg", "Crazy Eights", " <i class='icon-mobile-phone'></i> ", " <i class='icon-tablet'></i> ", " <i class='icon-desktop'></i> ", "image", "Crazy,Eights,Cards", typeCard],
            ["games/whist/index.html", "img/Whist.jpg", "Whist", " <i class='icon-mobile-phone'></i> ", " <i class='icon-tablet'></i> ", " <i class='icon-desktop'></i> ", "image", "Whist,Cards", typeCard],

            ["games/unblockmepack/index.html", "img/UnblockMe-Pack.jpg", "Unblock Me Pack", " <i class='icon-mobile-phone'></i> ", " <i class='icon-tablet'></i> ", " <i class='icon-desktop'></i>", "image", "Unblock,Me,Pack", typePuzzle],
            ["games/flowfreepack/index.html", "img/Flow-Free-Pack.jpg", "Flow Free Pack", " <i class='icon-mobile-phone'></i> ", " <i class='icon-tablet'></i> ", " <i class='icon-desktop'></i>", "image", "Flow,Pack", typePuzzle],
            ["games/flowbridgespack/index.html", "img/Flow-Bridges-Pack.jpg", "Flow Bridges Pack", " <i class='icon-mobile-phone'></i> ", " <i class='icon-tablet'></i> ", " <i class='icon-desktop'></i>", "image", "Flow,Bridges,Pack", typePuzzle],
            ["games/oneline/index.html", "img/One-Line.jpg", "One Line", " <i class='icon-mobile-phone'></i> ", " <i class='icon-tablet'></i> ", " <i class='icon-desktop'></i>", "image", "One,Line", typePuzzle],
            ["games/trace/index.html", "img/Fill-One-Line.jpg", "Fill 1 Line", " <i class='icon-mobile-phone'></i> ", " <i class='icon-tablet'></i> ", " <i class='icon-desktop'></i>", "image", "Fill,1,Line", typePuzzle],
            ["games/rope/index.html", "img/Line-Puzzle.jpg", "Line Puzzle", " <i class='icon-mobile-phone'></i> ", " <i class='icon-tablet'></i> ", " <i class='icon-desktop'></i>", "image", "Line,Puzzle", typePuzzle],
            ["games/unrollmepack/index.html", "img/UnrollMe-Pack.jpg", "Unroll Me Pack", " <i class='icon-mobile-phone'></i> ", " <i class='icon-tablet'></i> ", " <i class='icon-desktop'></i>", "image", "Unroll,Me,Pack", typePuzzle],

            ["games/minesweeper/index.html", "img/MineSweeper.jpg", "MineSweeper", "", "", " <i class='icon-desktop'></i> ", "image", "MineSweeper,Windows", typePuzzle],
            ["games/puzzles/index.html", "img/Jigsaw-Puzzle.jpg", "Jigsaw Puzzle", "", " <i class='icon-tablet'></i> ", " <i class='icon-desktop'></i> ", "image", "Jigsaw,Puzzle", typePuzzle],
            ["games/puzzles2/index.html", "img/Jigsaw-Puzzle-2.jpg", "Jigsaw Puzzle 2", "", " <i class='icon-tablet'></i> ", " <i class='icon-desktop'></i> ", "image", "Jigsaw,Puzzle,2", typePuzzle],
            ["games/wordsearch/index.html", "img/Word-Search.jpg", "Word Search", "", " <i class='icon-tablet'></i> ", " <i class='icon-desktop'></i> ", "image", "Word,Search", typeWord],
            ["games/wordsearch2/index.html", "img/Word-Search-2.jpg", "Word Search 2", "", " <i class='icon-tablet'></i> ", " <i class='icon-desktop'></i> ", "image", "Word,Search,2", typeWord],
            ["games/guess4in1/index.html", "img/Guess4IN1.jpg", "4 Pics 1 Word", " <i class='icon-mobile-phone'></i> ", " <i class='icon-tablet'></i> ", " <i class='icon-desktop'></i> ", "image", "4,Pics,IN,1,Word", typeWord],
            ["games/guess4in1v2/index.html", "img/Guess4IN1V2.jpg", "4 Pics 1 Word V2", " <i class='icon-mobile-phone'></i> ", " <i class='icon-tablet'></i> ", " <i class='icon-desktop'></i> ", "image", "4,Pics,IN,1,Word,V2", typeWord],

            ["games/caveadventure/index.html", "img/Cave-Adventure.jpg", "Cave Adventure", " <i class='icon-mobile-phone'></i> ", " <i class='icon-tablet'></i> ", " <i class='icon-desktop'></i> ", "image", "Cave,Adventure", typeMatch3],
            ["games/pipemaster/index.html", "img/Pipe-Master.jpg", "Pipe Master", " <i class='icon-mobile-phone'></i> ", " <i class='icon-tablet'></i> ", " <i class='icon-desktop'></i> ", "image", "Pipe,Master", typePuzzle],
            ["games/candyland/index.html", "img/Jelly-Farm.jpg", "Jelly Farm", " <i class='icon-mobile-phone'></i> ", " <i class='icon-tablet'></i> ", " <i class='icon-desktop'></i> ", "image", "Jelly,Farm", typeMatch3],
            ["games/jewelscrush/index.html", "img/Jewel-Crush.jpg", "Jewel Crush", " <i class='icon-mobile-phone'></i> ", " <i class='icon-tablet'></i> ", " <i class='icon-desktop'></i> ", "image", "Jewel,Crush", typeMatch3],

            ["games/wordpuzzle/index.html", "img/Word-Puzzle.jpg", "Crossword Puzzles", " <i class='icon-mobile-phone'></i> ", " <i class='icon-tablet'></i> ", " <i class='icon-desktop'></i>", "image", "Crossword,Puzzle", typeWord],
            ["games/wordpuzzle365/index.html", "img/Word-Puzzle-Daily.jpg", "Crossword Daily", " <i class='icon-mobile-phone'></i> ", " <i class='icon-tablet'></i> ", " <i class='icon-desktop'></i>", "image", "Crossword,Puzzle,Daily,365", typeWord],
            ["games/wordaholic/index.html", "img/Word-Aholic.jpg", "Word Fun", " <i class='icon-mobile-phone'></i> ", " <i class='icon-tablet'></i> ", " <i class='icon-desktop'></i>", "image", "Word,Fun", typeWord],
            ["games/wordquest/index.html", "img/Word-Quest.jpg", "Word Quest", " <i class='icon-mobile-phone'></i> ", " <i class='icon-tablet'></i> ", " <i class='icon-desktop'></i>", "image", "Word,Quest", typeWord],
            ["games/sudoku2/index.html", "img/Sudoku2.jpg", "Sudoku 2", " <i class='icon-mobile-phone'></i> ", " <i class='icon-tablet'></i> ", " <i class='icon-desktop'></i>", "image", "Sudoku,2", typePuzzle],

            ["games/guessit/index.html", "img/GuessIt.jpg", "What Pic?", " <i class='icon-mobile-phone'></i> ", " <i class='icon-tablet'></i> ", " <i class='icon-desktop'></i> ", "image", "What,Pic,?,Guess,It", typeWord],

            //["games/candyrain/index.html", "img/Candy-Blast.jpg", "Candy Blast", " <i class='icon-mobile-phone'></i> ", " <i class='icon-tablet'></i> ", " <i class='icon-desktop'></i> ", "img/work28.png", "Candy,Blast,Crush", typeMatch3],
            //["games/sudoku/index.html", "img/Sudoku.jpg", "Sudoku 1", " <i class='icon-mobile-phone'></i> ", " <i class='icon-tablet'></i> ", " <i class='icon-desktop'></i> ", "img/work73.png", "Sudoku,Puzzle", typePuzzle],

            //["games/feedcandy/index.html", "img/Candy-Eater.jpg", "Candy Eater", " <i class='icon-mobile-phone'></i> ", " <i class='icon-tablet'></i> ", " <i class='icon-desktop'></i> ", "img/work30.png", "Candy,Eater", typePuzzle],
            ["games/diamondcrush/index.html", "img/Diamond-Match.jpg", "Diamond Match", " <i class='icon-mobile-phone'></i> ", " <i class='icon-tablet'></i> ", " <i class='icon-desktop'></i> ", "image", "Diamond,Match", typeMatch3],
            ["games/gemcrushlegends/index.html", "img/Gem-Legends.jpg", "Gem Legends", " <i class='icon-mobile-phone'></i> ", " <i class='icon-tablet'></i> ", " <i class='icon-desktop'></i> ", "image", "Gem,Legends", typeMatch3],
            ["games/snaptheshape/index.html", "img/Shape-Puzzle.jpg", "Shape Puzzle", "", " <i class='icon-tablet'></i> ", " <i class='icon-desktop'></i> ", "image", "Shape,Puzzle", typePuzzle],
            //["games/candyworld3/index.html", "img/Candy-World.jpg", "Candy World", " <i class='icon-mobile-phone'></i> ", " <i class='icon-tablet'></i> ", " <i class='icon-desktop'></i> ", "img/work69.png", "Candy,World", typeMatch3],

            //["games/animalconnect/index.html", "img/Animal-Flow.jpg", "Animal Flow", " <i class='icon-mobile-phone'></i> ", " <i class='icon-tablet'></i> ", " <i class='icon-desktop'></i> ", "img/work29.png", "Animal,Flow", typePuzzle],
            //["games/wintermatch/index.html", "img/Winter-Match.jpg", "Winter Match", " <i class='icon-mobile-phone'></i> ", " <i class='icon-tablet'></i> ", " <i class='icon-desktop'></i> ", "img/work39.png", "Winter,Match", typeMatch3],
            ["games/rescuediver/index.html", "img/Rescue-Diver.jpg", "Rescue Diver", " <i class='icon-mobile-phone'></i> ", " <i class='icon-tablet'></i> ", " <i class='icon-desktop'></i> ", "image", "Rescue,Diver", typeMatch3],
            //["games/crazyjelly/index.html", "img/Crazy-Jelly.jpg", "Crazy Jelly", " <i class='icon-mobile-phone'></i> ", " <i class='icon-tablet'></i> ", " <i class='icon-desktop'></i> ", "img/work41.png", "Crazy,Jelly", typeMatch3],
            //["games/connectmefactory/index.html", "img/Connect-Me.jpg", "Connect Me", " <i class='icon-mobile-phone'></i> ", " <i class='icon-tablet'></i> ", " <i class='icon-desktop'></i> ", "img/work42.png", "Connect,Me", typePuzzle],

            //["games/jumpingstone/index.html", "img/Jumping-Stone.jpg", "Jumping Stone", " <i class='icon-mobile-phone'></i> ", " <i class='icon-tablet'></i> ", " <i class='icon-desktop'></i> ", "img/work49.png", "Jumping,Stone", typePuzzle],
            //["games/throwingball/index.html", "img/Throwing-Ball.jpg", "Throwing Ball", " <i class='icon-mobile-phone'></i> ", " <i class='icon-tablet'></i> ", " <i class='icon-desktop'></i> ", "img/work50.png", "Throwing,Ball", typePuzzle],
            ["games/candybomb/index.html", "img/Candy-Bomb.jpg", "Candy Bomb", " <i class='icon-mobile-phone'></i> ", " <i class='icon-tablet'></i> ", " <i class='icon-desktop'></i> ", "image", "Candy,Bomb", typePuzzle],
            //["games/catchball/index.html", "img/Catch-Ball.jpg", "Catch Ball", " <i class='icon-mobile-phone'></i> ", " <i class='icon-tablet'></i> ", " <i class='icon-desktop'></i> ", "img/work52.png", "Catch,Ball", typePuzzle],
            ["games/buildingpuzzle/index.html", "img/Building-Puzzle.jpg", "Building Puzzle", " <i class='icon-mobile-phone'></i> ", " <i class='icon-tablet'></i> ", " <i class='icon-desktop'></i> ", "image", "Building,Puzzle", typePuzzle],

            //["games/dangerroad/index.html", "img/Danger-Road.jpg", "Danger Road", " <i class='icon-mobile-phone'></i> ", " <i class='icon-tablet'></i> ", " <i class='icon-desktop'></i> ", "img/work38.png", "Danger,Road", typePuzzle],
            //["games/volcanoeruption/index.html", "img/Volcano-Eruption.jpg", "Volcano Eruption", " <i class='icon-mobile-phone'></i> ", " <i class='icon-tablet'></i> ", " <i class='icon-desktop'></i> ", "img/work44.png", "Volcano,Eruption", typePuzzle],
            //["games/gameaboutsquares/index.html", "img/Move-Squares.jpg", "Move Squares", " <i class='icon-mobile-phone'></i> ", " <i class='icon-tablet'></i> ", " <i class='icon-desktop'></i> ", "img/work45.png", "Move,Squares", typePuzzle],
            //["games/fluffyrescue/index.html", "img/Fluffy-Rescue.jpg", "Fluffy Rescue", " <i class='icon-mobile-phone'></i> ", " <i class='icon-tablet'></i> ", " <i class='icon-desktop'></i> ", "img/work46.png", "Fluffy,Rescue", typePuzzle],
            ["games/streammaster/index.html", "img/Stream-Master.jpg", "Stream Master", " <i class='icon-mobile-phone'></i> ", " <i class='icon-tablet'></i> ", " <i class='icon-desktop'></i> ", "image", "Stream,Master", typePuzzle],

            ["games/guessitkids/index.html", "img/GuessItKids.jpg", "What Pic? (Kids)", " <i class='icon-mobile-phone'></i> ", " <i class='icon-tablet'></i> ", " <i class='icon-desktop'></i> ", "image", "What,Pic,?,Guess,It,Kids", typeWord],
            ["games/guesscelebrity/index.html", "img/GuessCelebrity.jpg", "Guess Celebrity", " <i class='icon-mobile-phone'></i> ", " <i class='icon-tablet'></i> ", " <i class='icon-desktop'></i> ", "image", "Guess,Celebrity", typeWord],


            ["games/bouncebutton/index.html", "img/Bounce-Button.jpg", "Bounce Button", " <i class='icon-mobile-phone'></i> ", " <i class='icon-tablet'></i> ", " <i class='icon-desktop'></i> ", "image", "Bounce,Button", typePuzzle],
            ["games/pacman/index.html", "img/Pac-Squirrel.jpg", "Pac Squirrel", " <i class='icon-mobile-phone'></i> ", " <i class='icon-tablet'></i> ", " <i class='icon-desktop'></i> ", "image", "Pac,Squirrel", typePuzzle],
            ["games/donkeyjump/index.html", "img/Donkey-Jump.jpg", "Donkey Jump", "", "", " <i class='icon-desktop'></i> ", "image", "Donkey,Jump", typePuzzle],
            ["games/2048/index.html", "img/2048.jpg", "2048", " <i class='icon-mobile-phone'></i> ", " <i class='icon-tablet'></i> ", " <i class='icon-desktop'></i> ", "image", "2048", typePuzzle],
            ["games/pushflowerpot/index.html", "img/Push-Flowerpot.jpg", "Push Flowerpot", " <i class='icon-mobile-phone'></i> ", " <i class='icon-tablet'></i> ", " <i class='icon-desktop'></i> ", "image", "Push Flower", typePuzzle],

            //["games/flow/index.html", "img/work7.jpg", "Link Colors", "", "", " <i class='icon-desktop'></i> "],

            //["games/jellysplash/index.html", "img/Jelly-Splash.jpg", "Jelly Splash", " <i class='icon-mobile-phone'></i> ", " <i class='icon-tablet'></i> ", " <i class='icon-desktop'></i> ", "img/work60.png", "Jelly,Splash", typeMatch3],
            //["games/roadsafety/index.html", "img/Road-Safety.jpg", "Road Safety", " <i class='icon-mobile-phone'></i> ", " <i class='icon-tablet'></i> ", " <i class='icon-desktop'></i> ", "img/work61.png", "Road,Safety", typePuzzle],
            //["games/spinjump/index.html", "img/Spin-Jump.jpg", "Spin Jump", " <i class='icon-mobile-phone'></i> ", " <i class='icon-tablet'></i> ", " <i class='icon-desktop'></i> ", "img/work62.png", "Spin,Jump", typePuzzle],

            ["games/caveadventure2/index.html", "img/Cave-Adventure-2.jpg", "Cave Adventure 2", " <i class='icon-mobile-phone'></i> ", " <i class='icon-tablet'></i> ", " <i class='icon-desktop'></i> ", "image", "Cave,Adventure,2", typeMatch3],
            //["games/lovematch/index.html", "img/Love-Match.jpg", "Love Match", " <i class='icon-mobile-phone'></i> ", " <i class='icon-tablet'></i> ", " <i class='icon-desktop'></i> ", "img/work55.png", "Love,Match", typeMatch3],
            //["games/jumphero/index.html", "img/Jump-Hero.jpg", "Jump Hero", " <i class='icon-mobile-phone'></i> ", " <i class='icon-tablet'></i> ", " <i class='icon-desktop'></i> ", "img/work56.png", "Jump,Hero", typePuzzle],
            ["games/carrotpush/index.html", "img/Carrot-Push.jpg", "Carrot Push", " <i class='icon-mobile-phone'></i> ", " <i class='icon-tablet'></i> ", " <i class='icon-desktop'></i> ", "image", "Carrot,Push", typePuzzle],
            ["games/rescuediver2/index.html", "img/Rescue-Diver-2.jpg", "Rescue Diver 2", " <i class='icon-mobile-phone'></i> ", " <i class='icon-tablet'></i> ", " <i class='icon-desktop'></i> ", "image", "Rescue,Diver,2", typeMatch3],


            //["games/bubbleshooter/index.html", "img/Bubble-Shooter.jpg", "Bubble Shooter", " <i class='icon-mobile-phone'></i> ", " <i class='icon-tablet'></i> ", " <i class='icon-desktop'></i> ", "img/work24.png", "Bubble,Shooter", typeMatch3],
            ["games/4inline/index.html", "img/Four-in-Line.jpg", "Four in Line", " <i class='icon-mobile-phone'></i> ", " <i class='icon-tablet'></i> ", " <i class='icon-desktop'></i> ", "image", "Four,In,Line", typePuzzle],
            //["games/mandown/index.html", "img/Man-Down.jpg", "Man Down", "", "", " <i class='icon-desktop'></i> ", "img/work16.png", "Man,Down", typePuzzle],
            ["games/torus/index.html", "img/Torus.jpg", "Torus", "", "", " <i class='icon-desktop'></i> ", "image", "Torus,Tetris", typePuzzle],

            //["games/trackbuilder/index.html", "img/Trail-Builder.jpg", "Trail Builder", " <i class='icon-mobile-phone'></i> ", " <i class='icon-tablet'></i> ", " <i class='icon-desktop'></i> ", "img/work63.png", "Trail,Builder", typePuzzle],
            //["games/taxipickup/index.html", "img/Taxi-Pickup.jpg", "Taxi Pickup", " <i class='icon-mobile-phone'></i> ", " <i class='icon-tablet'></i> ", " <i class='icon-desktop'></i> ", "img/work64.png", "Taxi,Pickup", typePuzzle],
            //["games/elevatorminer/index.html", "img/Miner-Adventure.jpg", "Miner Adventure", " <i class='icon-mobile-phone'></i> ", " <i class='icon-tablet'></i> ", " <i class='icon-desktop'></i> ", "img/work65.png", "Miner,Adventure", typePuzzle],
            ["games/letmegrowup/index.html", "img/Let-Me-Growup.jpg", "Let Me Growup", " <i class='icon-mobile-phone'></i> ", " <i class='icon-tablet'></i> ", " <i class='icon-desktop'></i> ", "image", "Let,Me,Growup", typePuzzle],
            //["games/jellymatch/index.html", "img/Jelly-Match.jpg", "Jelly Match", " <i class='icon-mobile-phone'></i> ", " <i class='icon-tablet'></i> ", " <i class='icon-desktop'></i> ", "img/work34.png", "Jelly,Match", typeMatch3],
            //["games/sweetdream/index.html", "img/Sweet-Dream.jpg", "Sweet Dream", " <i class='icon-mobile-phone'></i> ", " <i class='icon-tablet'></i> ", " <i class='icon-desktop'></i> ", "img/work33.png", "Sweet Dream", typePuzzle],
            //["games/doodleconnect/index.html", "img/Draw-Connect.jpg", "Draw Connect", " <i class='icon-mobile-phone'></i> ", " <i class='icon-tablet'></i> ", " <i class='icon-desktop'></i> ", "img/work37.png", "Draw,Connect", typePuzzle],
            //["games/flywithrope/index.html", "img/Fly-With-Rope.jpg", "Fly With Rope", " <i class='icon-mobile-phone'></i> ", " <i class='icon-tablet'></i> ", " <i class='icon-desktop'></i> ", "img/work70.png", "Fly,With,Rope", typePuzzle]

            ["games/blackjack/index.html", "img/Black-Jack.jpg", "Black Jack", " <i class='icon-mobile-phone'></i> ", " <i class='icon-tablet'></i> ", " <i class='icon-desktop'></i> ", "image", "Black,Jack,Cards", typeCard],
            ["games/reversi/index.html", "img/Reversi.jpg", "Reversi", " <i class='icon-mobile-phone'></i> ", " <i class='icon-tablet'></i> ", " <i class='icon-desktop'></i> ", "image", "Reversi", typePuzzle],
            ["games/oldmaid/index.html", "img/Old-Maid.jpg", "Old Maid", " <i class='icon-mobile-phone'></i> ", " <i class='icon-tablet'></i> ", " <i class='icon-desktop'></i> ", "image", "Old,Maid", typePuzzle],
            ["games/mahjongunlimited/index.html", "img/Mahjong-Unlimited.jpg", "Mahjong Unlimited", " <i class='icon-mobile-phone'></i> ", " <i class='icon-tablet'></i> ", " <i class='icon-desktop'></i> ", "image", "Mahjong,Solitaire,Unlimited,Windows,Cards", typeCard],
            ["games/guess2in1/index.html", "img/Guess2IN1.jpg", "2 Pics 1 Word", " <i class='icon-mobile-phone'></i> ", " <i class='icon-tablet'></i> ", " <i class='icon-desktop'></i>", "image", "2,Pics,IN,1,Word", typeWord],
            ["games/guess2in1v2/index.html", "img/Guess2IN1V2.jpg", "2 Pics 1 Word V2", " <i class='icon-mobile-phone'></i> ", " <i class='icon-tablet'></i> ", " <i class='icon-desktop'></i>", "image", "2,Pics,IN,1,Word,V2", typeWord],

            ["games/blockpuzzle/index.html", "img/Block-Puzzle.jpg", "Block Puzzle", " <i class='icon-mobile-phone'></i> ", " <i class='icon-tablet'></i> ", " <i class='icon-desktop'></i>", "image", "Block,Puzzle", typePuzzle],
            ["games/wordconnect/index.html", "img/Word-Connect.jpg", "Word Connect", " <i class='icon-mobile-phone'></i> ", " <i class='icon-tablet'></i> ", " <i class='icon-desktop'></i>", "image", "Word,Connect", typeWord],


            ["games/solitairepack/bakersgame.html", "img/BakersGame.jpg", "Baker's Game", " <i class='icon-mobile-phone'></i> ", " <i class='icon-tablet'></i> ", " <i class='icon-desktop'></i> ", "image", "Baker,Game,Solitaire,Cards", typeCard],
            ["games/solitairepack/diplomat.html", "img/Diplomat.jpg", "Diplomat Solitaire", " <i class='icon-mobile-phone'></i> ", " <i class='icon-tablet'></i> ", " <i class='icon-desktop'></i> ", "image", "Diplomat,Solitaire,Cards", typeCard],
            ["games/solitairepack/easthaven.html", "img/Easthaven.jpg", "Easthaven Solitaire", " <i class='icon-mobile-phone'></i> ", " <i class='icon-tablet'></i> ", " <i class='icon-desktop'></i> ", "image", "Easthaven,Solitaire,Cards", typeCard],
            ["games/solitairepack/eightoff.html", "img/EightOff.jpg", "EightOff Solitaire", " <i class='icon-mobile-phone'></i> ", " <i class='icon-tablet'></i> ", " <i class='icon-desktop'></i> ", "image", "EightOff,Solitaire,Cards", typeCard],
            ["games/solitairepack/flowergarden.html", "img/FlowerGarden.jpg", "Flower Garden", " <i class='icon-mobile-phone'></i> ", " <i class='icon-tablet'></i> ", " <i class='icon-desktop'></i> ", "image", "Flower,Garden,Solitaire,Cards", typeCard],
            ["games/solitairepack/fortythieves.html", "img/FortyThieves.jpg", "Forty Thieves", " <i class='icon-mobile-phone'></i> ", " <i class='icon-tablet'></i> ", " <i class='icon-desktop'></i> ", "image", "Forty,Thieves,Solitaire,Cards", typeCard],
            ["games/solitairepack/freecell2deck.html", "img/FreeCell2Deck.jpg", "FreeCell 2 Decks", " <i class='icon-mobile-phone'></i> ", " <i class='icon-tablet'></i> ", " <i class='icon-desktop'></i> ", "image", "FreeCell,2,Decks,Solitaire,Cards", typeCard],
            ["games/solitairepack/golfeasy.html", "img/GolfEasy.jpg", "Golf Solitaire", " <i class='icon-mobile-phone'></i> ", " <i class='icon-tablet'></i> ", " <i class='icon-desktop'></i> ", "image", "Golf,Solitaire,Cards", typeCard],
            ["games/solitairepack/scorpion.html", "img/Scorpion.jpg", "Scorpion Solitaire", " <i class='icon-mobile-phone'></i> ", " <i class='icon-tablet'></i> ", " <i class='icon-desktop'></i> ", "image", "Scorpion,Solitaire,Cards", typeCard],
            ["games/solitairepack/simplesimon.html", "img/SimpleSimon.jpg", "Simple Simon", " <i class='icon-mobile-phone'></i> ", " <i class='icon-tablet'></i> ", " <i class='icon-desktop'></i> ", "image", "Simple,Simon,Solitaire,Cards", typeCard],
            ["games/solitairepack/yukon.html", "img/Yukon.jpg", "Yukon Solitaire", " <i class='icon-mobile-phone'></i> ", " <i class='icon-tablet'></i> ", " <i class='icon-desktop'></i> ", "image", "Yukon,Solitaire,Cards", typeCard],

            ["games/solitairepack/freecell2.html", "img/FreeCell.jpg", "FreeCell", " <i class='icon-mobile-phone'></i> ", " <i class='icon-tablet'></i> ", " <i class='icon-desktop'></i> ", "image", "FreeCell,Solitaire,Windows,Cards", typeCard],
            ["games/solitairepack/pyramid2.html", "img/Pyramid.jpg", "Pyramid Easy", " <i class='icon-mobile-phone'></i> ", " <i class='icon-tablet'></i> ", " <i class='icon-desktop'></i> ", "image", "Pyramid,Easy,Solitaire,Windows,Cards", typeCard],
            ["games/solitairepack/klondike2.html", "img/Klondike.jpg", "Klondike", " <i class='icon-mobile-phone'></i> ", " <i class='icon-tablet'></i> ", " <i class='icon-desktop'></i> ", "image", "Klondike,Solitaire,Windows,Cards", typeCard],
            ["games/solitairepack/Spider2.html", "img/Spider.jpg", "Spider", " <i class='icon-mobile-phone'></i> ", " <i class='icon-tablet'></i> ", " <i class='icon-desktop'></i> ", "image", "Spider,Solitaire,Windows,Cards", typeCard],



            ["games/ginrummy/index.html", "img/Gin-Rummy.jpg", "Gin Rummy", " <i class='icon-mobile-phone'></i> ", " <i class='icon-tablet'></i> ", " <i class='icon-desktop'></i> ", "image", "Gin,Rummy,Cards", typeCard],

            ["games/unblockme/index.html", "img/UnblockMe.jpg", "Unblock Me", " <i class='icon-mobile-phone'></i> ", " <i class='icon-tablet'></i> ", " <i class='icon-desktop'></i>", "image", "Unblock,Me", typePuzzle],
            ["games/bridges/index.html", "img/Bridges.jpg", "Bridges", " <i class='icon-mobile-phone'></i> ", " <i class='icon-tablet'></i> ", " <i class='icon-desktop'></i>", "image", "Bridges", typePuzzle],
            ["games/unrollme/index.html", "img/UnrollMe.jpg", "Unroll Me", " <i class='icon-mobile-phone'></i> ", " <i class='icon-tablet'></i> ", " <i class='icon-desktop'></i>", "image", "Unroll,Me", typePuzzle],
            ["games/blockfill/index.html", "img/Block-Fill.jpg", "Block Fill", " <i class='icon-mobile-phone'></i> ", " <i class='icon-tablet'></i> ", " <i class='icon-desktop'></i>", "image", "Block,Fill", typePuzzle],
            ["games/shapefill/index.html", "img/Shape-Fill.jpg", "Shape Fill", " <i class='icon-mobile-phone'></i> ", " <i class='icon-tablet'></i> ", " <i class='icon-desktop'></i>", "image", "Shape,Fill", typePuzzle],
            ["games/plumberpuzzle/index.html", "img/Plumber-Puzzle.jpg", "Plumber Puzzle", " <i class='icon-mobile-phone'></i> ", " <i class='icon-tablet'></i> ", " <i class='icon-desktop'></i>", "image", "Plumber,Puzzle", typePuzzle],
            ["games/flowfree/index.html", "img/Flow-Free.jpg", "Flow", " <i class='icon-mobile-phone'></i> ", " <i class='icon-tablet'></i> ", " <i class='icon-desktop'></i>", "image", "Flow", typePuzzle],
            ["games/flowbridges/index.html", "img/Flow-Bridges.jpg", "Flow Bridges", " <i class='icon-mobile-phone'></i> ", " <i class='icon-tablet'></i> ", " <i class='icon-desktop'></i>", "image", "Flow,Bridges", typePuzzle],
            ["games/dots/index.html", "img/Dots.jpg", "Find a Way", " <i class='icon-mobile-phone'></i> ", " <i class='icon-tablet'></i> ", " <i class='icon-desktop'></i>", "image", "Find,a,Way", typePuzzle],
            ["games/sokoban/index.html", "img/Sokoban.jpg", "Sokoban", " <i class='icon-mobile-phone'></i> ", " <i class='icon-tablet'></i> ", " <i class='icon-desktop'></i>", "image", "Sokoban", typePuzzle],
            ["games/maze/index.html", "img/Maze.jpg", "Maze", " <i class='icon-mobile-phone'></i> ", " <i class='icon-tablet'></i> ", " <i class='icon-desktop'></i>", "image", "Maze", typePuzzle],


            ["games/videopoker/index.html", "img/Video-Poker.jpg", "Video Poker", " <i class='icon-mobile-phone'></i> ", " <i class='icon-tablet'></i> ", " <i class='icon-desktop'></i>", "image", "Video,Poker", typeCard],

            ["games/blast/index.html", "img/TriPeaks-Blast.jpg", "TriPeaks Blast", " <i class='icon-mobile-phone'></i> ", " <i class='icon-tablet'></i> ", " <i class='icon-desktop'></i> ", "image", "TriPeaks,Blast,Solitaire,Windows,Cards", typeCard],
            ["games/freecellblast/index.html", "img/FreeCell-Blast.jpg", "FreeCell Blast", " <i class='icon-mobile-phone'></i> ", " <i class='icon-tablet'></i> ", " <i class='icon-desktop'></i> ", "image", "FreeCell,Blast,Solitaire,Windows,Cards", typeCard],
            ["games/pyramidblast/index.html", "img/Pyramid-Blast.jpg", "Pyramid Blast", " <i class='icon-mobile-phone'></i> ", " <i class='icon-tablet'></i> ", " <i class='icon-desktop'></i> ", "image", "Pyramid,Blast,Solitaire,Windows,Cards", typeCard],
            ["games/klondikeblast/index.html", "img/Klondike-Blast.jpg", "Klondike Blast", " <i class='icon-mobile-phone'></i> ", " <i class='icon-tablet'></i> ", " <i class='icon-desktop'></i> ", "image", "Klondike,Blast,Solitaire,Windows,Cards", typeCard],
            ["games/spiderblast/index.html", "img/Spider-Blast.jpg", "Spider Blast", " <i class='icon-mobile-phone'></i> ", " <i class='icon-tablet'></i> ", " <i class='icon-desktop'></i> ", "image", "Spider,Blast,Solitaire,Windows,Cards", typeCard],


            ["games/wordsearch3/index.html", "img/Word-Search-Unlimited.jpg", "Word Search 3", " <i class='icon-mobile-phone'></i> ", " <i class='icon-tablet'></i> ", " <i class='icon-desktop'></i> ", "image", "Word,Search,3", typeWord],
            ["games/finddiff/index.html", "img/Find-Diff.jpg", "Find Differences 1", " <i class='icon-mobile-phone'></i> ", " <i class='icon-tablet'></i> ", " <i class='icon-desktop'></i> ", "image", "Find,Differences", typePuzzle],

            ["games/ludoking/index.html", "img/Ludo.jpg", "Ludo King", " <i class='icon-mobile-phone'></i> ", " <i class='icon-tablet'></i> ", " <i class='icon-desktop'></i> ", "image", "Ludo,King", typePuzzle],
            ["games/2048solitaire/index.html", "img/2048Solitaire.jpg", "2048 Solitaire", " <i class='icon-mobile-phone'></i> ", " <i class='icon-tablet'></i> ", " <i class='icon-desktop'></i> ", "image", "2048,Solitaire", typePuzzle],



            ["games/mahjongdeluxe/index.html", "img/MahjongDeluxe.jpg", "Mahjong Deluxe", " <i class='icon-mobile-phone'></i> ", " <i class='icon-tablet'></i> ", " <i class='icon-desktop'></i> ", "image", "Mahjong,Deluxe", typeCard],
            ["games/ballz/index.html", "img/Ballz.jpg", "Ballz", " <i class='icon-mobile-phone'></i> ", " <i class='icon-tablet'></i> ", " <i class='icon-desktop'></i> ", "image", "Ballz", typePuzzle],
            ["games/bingo/index.html", "img/Bingo.jpg", "Bingo", " <i class='icon-mobile-phone'></i> ", " <i class='icon-tablet'></i> ", " <i class='icon-desktop'></i> ", "image", "Bingo", typePuzzle],
            ["games/domino_block/index.html", "img/Domino.jpg", "Domino", " <i class='icon-mobile-phone'></i> ", " <i class='icon-tablet'></i> ", " <i class='icon-desktop'></i> ", "image", "Domino", typePuzzle],
            ["games/treasurematch/index.html", "img/TreasureMatch.jpg", "Treasure Match", " <i class='icon-mobile-phone'></i> ", " <i class='icon-tablet'></i> ", " <i class='icon-desktop'></i> ", "image", "Treasure,Match", typeMatch3],
            ["games/loveballs/index.html", "img/LoveBalls.jpg", "Love Balls", " <i class='icon-mobile-phone'></i> ", " <i class='icon-tablet'></i> ", " <i class='icon-desktop'></i> ", "image", "Love,Balls", typePuzzle],
            ["games/uno/index.html", "img/Uno.jpg", "UNO", " <i class='icon-mobile-phone'></i> ", " <i class='icon-tablet'></i> ", " <i class='icon-desktop'></i> ", "image", "UNO", typeCard],
            ["games/pool/index.html", "img/Pool.jpg", "Pool", "", "", " <i class='icon-desktop'></i> ", "image", "Pool", typePuzzle],

            ["games/finddiff2/index.html", "img/Find-Diff-2.jpg", "Find Differences 2", " <i class='icon-mobile-phone'></i> ", " <i class='icon-tablet'></i> ", " <i class='icon-desktop'></i> ", "image", "Find,Differences,2", typePuzzle],
            ["games/finddiff3/index.html", "img/Find-Diff-3.jpg", "Find Differences 3", " <i class='icon-mobile-phone'></i> ", " <i class='icon-tablet'></i> ", " <i class='icon-desktop'></i> ", "image", "Find,Differences,3", typePuzzle],


            ["games/glasswater/index.html", "img/Glass-Water.jpg", "Glass & Water", " <i class='icon-mobile-phone'></i> ", " <i class='icon-tablet'></i> ", " <i class='icon-desktop'></i> ", "", "Glass,Water", typePuzzle],
            ["games/jellymatch2/index.html", "img/Jelly-Match-2.jpg", "Jelly Match 2", " <i class='icon-mobile-phone'></i> ", " <i class='icon-tablet'></i> ", " <i class='icon-desktop'></i> ", "", "Jelly,Match,2", typeMatch3],
            ["games/forestadventure/index.html", "img/Forest-Adventure.jpg", "Forest Adventure", " <i class='icon-mobile-phone'></i> ", " <i class='icon-tablet'></i> ", " <i class='icon-desktop'></i> ", "", "Forest,Adventure", typeMatch3],
            ["games/jellycrush/index.html", "img/Jelly-Crush.jpg", "Jelly Crush", " <i class='icon-mobile-phone'></i> ", " <i class='icon-tablet'></i> ", " <i class='icon-desktop'></i> ", "", "Jelly,Crush", typeMatch3],
            ["games/mahjong2/index.html", "img/Mahjong2.jpg", "Mahjong 2", " <i class='icon-mobile-phone'></i> ", " <i class='icon-tablet'></i> ", " <i class='icon-desktop'></i> ", "", "Mahjong,Solitaire,2", typeCard],
            ["games/hearts2/index.html", "img/Hearts2.jpg", "Hearts 2", " <i class='icon-mobile-phone'></i> ", " <i class='icon-tablet'></i> ", " <i class='icon-desktop'></i> ", "", "Hearts,2", typeCard],
            ["games/spades2/index.html", "img/Spades2.jpg", "Spades 2", " <i class='icon-mobile-phone'></i> ", " <i class='icon-tablet'></i> ", " <i class='icon-desktop'></i> ", "", "Spades,2", typeCard],

            ["games/bubbleshooter/index.html", "img/Bubble-Shooter.jpg", "Bubble Shooter", " <i class='icon-mobile-phone'></i> ", " <i class='icon-tablet'></i> ", " <i class='icon-desktop'></i> ", "", "Bubble,Shooter", typeMatch3],
            ["games/jewelmaya/index.html", "img/Maya-Match.jpg", "Maya Match", " <i class='icon-mobile-phone'></i> ", " <i class='icon-tablet'></i> ", " <i class='icon-desktop'></i> ", "", "Maya,Match", typeMatch3],
            ["games/checkers/index.html", "img/Checkers.jpg", "Checkers", " <i class='icon-mobile-phone'></i> ", " <i class='icon-tablet'></i> ", " <i class='icon-desktop'></i> ", "", "Checkers", typeCard],
            ["games/backgammon/index.html", "img/Backgammon.jpg", "Backgammon", " <i class='icon-mobile-phone'></i> ", " <i class='icon-tablet'></i> ", " <i class='icon-desktop'></i> ", "", "Backgammon", typeCard],
            ["games/chess/index.html", "img/Chess.jpg", "Chess", " <i class='icon-mobile-phone'></i> ", " <i class='icon-tablet'></i> ", " <i class='icon-desktop'></i> ", "", "Chess", typeCard],
            ["games/ballsort/index.html", "img/BallSort.jpg", "Ball Sort", " <i class='icon-mobile-phone'></i> ", " <i class='icon-tablet'></i> ", " <i class='icon-desktop'></i> ", "", "Ball,Sort", typePuzzle],

            ["games/mahjong3d/index.html", "img/Mahjong3D.jpg", "Mahjong 3D", " <i class='icon-mobile-phone'></i> ", " <i class='icon-tablet'></i> ", " <i class='icon-desktop'></i> ", "", "Mahjong,3D", typeCard],
            ["games/tooncup/index.html", "img/Cartoon_Football.jpg", "Cartoon Football", " <i class='icon-mobile-phone'></i> ", " <i class='icon-tablet'></i> ", " <i class='icon-desktop'></i> ", "", "Cartoon,Football", typePuzzle],
            ["games/candyblast2/index.html", "img/Candy_Blast2.jpg", "Candy Jelly", " <i class='icon-mobile-phone'></i> ", " <i class='icon-tablet'></i> ", " <i class='icon-desktop'></i> ", "", "Candy,Jelly", typeMatch3],
            ["games/candyflipworld/index.html", "img/Candy_Flip.jpg", "Candy Rotation", " <i class='icon-mobile-phone'></i> ", " <i class='icon-tablet'></i> ", " <i class='icon-desktop'></i> ", "", "Candy,Rotation", typePuzzle],
            ["games/daytowerrush/index.html", "img/Dino_Attack.jpg", "Tower Defense", " <i class='icon-mobile-phone'></i> ", " <i class='icon-tablet'></i> ", " <i class='icon-desktop'></i> ", "", "Tower,Defense", typePuzzle],

            ["games/jewelburst/index.html", "img/DiamondGalaxy.jpg", "Diamond Galaxy", " <i class='icon-mobile-phone'></i> ", " <i class='icon-tablet'></i> ", " <i class='icon-desktop'></i> ", "", "Diamond,Galaxy", typeMatch3],
            ["games/candybubble/index.html", "img/CandyShooter.jpg", "Candy Shooter", " <i class='icon-mobile-phone'></i> ", " <i class='icon-tablet'></i> ", " <i class='icon-desktop'></i> ", "", "Candy,Shooter", typeMatch3],
            ["games/crazyracing/index.html", "img/CarRacing.jpg", "Car Racing", " <i class='icon-mobile-phone'></i> ", " <i class='icon-tablet'></i> ", " <i class='icon-desktop'></i> ", "", "Car,Racing", typePuzzle],
            ["games/gemsaga/index.html", "img/JewelAdventure.jpg", "Jewel Adventure", " <i class='icon-mobile-phone'></i> ", " <i class='icon-tablet'></i> ", " <i class='icon-desktop'></i> ", "", "Jewel,Adventure", typeMatch3],

            ["games/jewelcandy/index.html", "img/JewelCandy.jpg", "Jewel Candy", " <i class='icon-mobile-phone'></i> ", " <i class='icon-tablet'></i> ", " <i class='icon-desktop'></i> ", "", "Jewel,Candy", typeMatch3],
            ["games/toymatch/index.html", "img/DollMatch.jpg", "Doll Adventure", " <i class='icon-mobile-phone'></i> ", " <i class='icon-tablet'></i> ", " <i class='icon-desktop'></i> ", "", "Doll,Adventure", typeMatch3],
            ["games/bubblehero/index.html", "img/BubbleBlast.jpg", "Bubble Blast", " <i class='icon-mobile-phone'></i> ", " <i class='icon-tablet'></i> ", " <i class='icon-desktop'></i> ", "", "Bubble,Blast", typeMatch3],
            ["games/basketballstars/index.html", "img/ShootGame.jpg", "Shoot Game", " <i class='icon-mobile-phone'></i> ", " <i class='icon-tablet'></i> ", " <i class='icon-desktop'></i> ", "", "Shoot,Game", typePuzzle],
            ["games/throwball/index.html", "img/ThrowBall.jpg", "Ball Mission", " <i class='icon-mobile-phone'></i> ", " <i class='icon-tablet'></i> ", " <i class='icon-desktop'></i> ", "", "Ball,Mission", typePuzzle],
            ["games/armydefense/index.html", "img/ArmyDefense.jpg", "Army Defense", " <i class='icon-mobile-phone'></i> ", " <i class='icon-tablet'></i> ", " <i class='icon-desktop'></i> ", "", "Army,Defense", typePuzzle],
            ["games/marblegame/index.html", "img/MarbleGame.jpg", "Marble Game", " <i class='icon-mobile-phone'></i> ", " <i class='icon-tablet'></i> ", " <i class='icon-desktop'></i> ", "", "Marble,Game", typeMatch3]

        ];

        return global_all_xp_games;
    }

    function createRecommendedGames() {

        //if (document.getElementById("myAds") !== null) {
        //    return;
        //}

        var global_all_xp_games = getAllGamges();

        sortRecommendGame(global_all_xp_games);

        for (var i = 0; i < global_all_xp_games.length; i++) {
            global_all_xp_games[i][0] = "../../" + global_all_xp_games[i][0];
            global_all_xp_games[i][1] = "../../" + global_all_xp_games[i][1];
        }

        renderRecommendedGames(global_all_xp_games);

        document.getElementById("myAds").style.display = "block";
    }

    function sortRecommendGame(gameAll) {

        var gamePlayedList = JSON.parse(localStorage.getItem("xpPlayedGame"));

        if (gamePlayedList != null) {

            var sortId = 1;
            var typeArray = [];

            // Played Games Sort - Get top 6 played games and cut first one at the end
            for (var i = 0; gamePlayedList != null && i < Math.min(gamePlayedList.length, 6); i++) {
                for (var j = 0; j < gameAll.length; j++) {
                    if (("games/" + gamePlayedList[i]) == gameAll[j][0]) {
                        gameAll[j][9] = sortId++;
                        typeArray.push(gameAll[j][8]);
                    }
                }
            }

            // Recommended Games Sort
            if (typeArray.length > 0) {
                sortId = 1000;
                var rType = typeArray[0];

                for (var j = 0; j < gameAll.length; j++) {
                    if (gameAll[j][8] == rType && gameAll[j][9] == null) {
                        gameAll[j][9] = sortId++;
                    }
                }
            }

            // Other Games Sort
            sortId = 10000;
            for (var j = 0; j < gameAll.length; j++) {
                if (gameAll[j][9] == null) {
                    gameAll[j][9] = sortId++;
                }
            }

            gameAll.sort(function (a, b) { return parseInt(a[9]) - parseInt(b[9]); });

            // Move First to Last
            gameAll.push(gameAll.shift());
        }

    }

    function renderRecommendedGames(global_all_xp_games) {

        var cols = 3;

        if ($(window).width() > $(window).height()) {
            cols = 4;
        }

        var htmlItem = '<td><div><p style="margin: 5px 0 10px;box-sizing: border-box;"><a href="{0}" onclick="window.location.href=\'{0}\'"><img src="{1}" style="border-radius:5px; width:' + (gAdsWidth / cols - 12) +
            'px;border: 1px solid white;padding:2px;box-sizing: border-box;" alt="{2}" onmouseover="this.style.border=\'1px solid #337ab7\'" onmouseout="this.style.border=\'1px solid white\'" /></a></p><p style="margin: 5px 0 10px;"><a style="color:white;font-family:Arial,Helvetica,sans-serif;font-size:1em;text-shadow:none;text-decoration: underline;" href="{0}" onclick="window.location.href=\'{0}\'">{2}</a></p></div></td>';
        var gamesInnerHtml = "";

        for (var i = 0; i < cols * 2; i++) {
            var gameRow = global_all_xp_games[i];
            var gameRowCount = gameRow.length;

            var temp = htmlItem;

            for (var j = 0; j < gameRowCount; j++) {
                temp = temp.split("{" + j + "}").join(gameRow[j]);
            }
            gamesInnerHtml += ((i == 0 || i == cols) ? "<tr>" : "") + temp + ((i == (cols - 1) || i == (cols * 2 - 1)) ? "</tr>" : "");
        }

        $('#adContainer').empty();

        $('#adContainer').html('<br/><table style="width:100%;border-collapse:initial">' + gamesInnerHtml + '</table>');
    }

    return {
        createRecommendedGames: createRecommendedGames,
        getAllGamges: getAllGamges
    };

}());

function popXpAdsOnly() {
    adBreak({
        type: 'next',  // ad shows at start of next level or restart of the game
        name: 'restart-game'
        // it's good practice to mute and pause gameplay
    });
    console.log("popXpAdsOnly");
}
function showXpAds() {

    setTimeout(function (e) {
        if (global_is_show_popup_games == true) {
            if (Math.random() < 0.8) {
                global_ads_count_down = 10;
                recommendedGamesController.createRecommendedGames();
                popXpAdsOnly();
            }
            else {
                popXpAdsOnly();
            }
            global_is_show_popup_ads = false;
            global_is_show_popup_games = false;

        }
    }, 1000);
}

function closeXpAds() {
    document.getElementById("myAds").style.display = "none";

    //$('#adContainer').empty();

    //try { window.dispatchEvent(new Event("focus")); } catch (err) { console.log(err); }
}

$(document).ready(function () {

    renderAds();

    enablePopupAds();

    adsCountDown();

    //checkAdBlock();

    //showXpAds();
    //setTimeout(function (e) { showXpAds(); }, 3000);
    //setTimeout(function (e) { popXpAdsOnly(); }, 7000);

    function renderAds() {

        $('head').append('<link href="../../ads/afg.css" rel="stylesheet" />');

        if ($(window).width() > $(window).height()) {
            gAdsWidth = $(window).width() * 0.7;
        } else {
            gAdsWidth = $(window).width() * 0.95;
        }

        gAdsHeight = $(window).height() * 0.7;

        var url = window.location.href;

        $('body').append(
            '<div id="myAds" class="overlay" style="display:none; ">' +
            '<a href="javascript:void(0)" class="adscountbtn" style="font-family:Arial,Helvetica,sans-serif;text-shadow:none;"></a>' +
            '<a href="javascript:void(0)" class="adsclosebtn" id="btnCloseAds" style="font-family:Arial,Helvetica,sans-serif;text-shadow:none;">&times;</a>' +

            '<div class="overlay-content" >' +
            '<div style="position:relative; margin:0 auto;height:' + gAdsHeight + 'px;width:' + gAdsWidth + 'px;">' +
            '<div id="adContainer" style="position: absolute;top: 0px;left: 0px;height:' + gAdsHeight + 'px;width:' + gAdsWidth + 'px;">' +
            '</div>' +
            '</div>' +
            '</div>' +
            '</div>');
    }

    function enablePopupAds() {
        setInterval(function (e) {
            global_is_show_popup_games = true;
        }, global_pop_up_span);

        setInterval(function (e) {
            if (global_is_show_popup_ads == false) {
                try {
                    global_is_show_popup_ads = true;
                } catch (err) { console.log(err); }
            }
        }, global_pop_up_span * 2);

        enableHTML5Ads();
    }

    function enableHTML5Ads() {
        var html5_script = document.createElement('script');

        html5_script.onload = function () {
            if (adsbygoogle && !adsbygoogle.loaded)
                window.adsbygoogle = window.adsbygoogle || [];
            adBreak = adConfig = function (o) { adsbygoogle.push(o); }
            //adConfig({ preloadAdBreaks: 'on' });
            setTimeout(function () { adConfig({ preloadAdBreaks: 'on' }); }, 5000);
        };

        html5_script.setAttribute('src', 'https://pagead2.googlesyndication.com/pagead/js/adsbygoogle.js');
        html5_script.setAttribute('async', 'true');
        //html5_script.setAttribute('data-adbreak-test', 'on');
        html5_script.setAttribute('data-ad-client', 'ca-pub-6289839502769272');
        html5_script.setAttribute('crossorigin', 'anonymous');

        document.head.appendChild(html5_script);

        //window.adsbygoogle = window.adsbygoogle || [];
    }

    function adsCountDown() {
        setInterval(function (e) {
            if (global_ads_count_down >= 0) {
                $('.adscountbtn').html('Suggested games will close in ' + (global_ads_count_down--));
            } else {
                closeXpAds();
            }
        }, 1000);
    }

    $('#btnCloseAds').click(function (e) {
        closeXpAds();
    });

    $('#myAds').click(function (e) {
        //console.log("myads clicked");
        setTimeout(function (e) { closeXpAds(); }, 200);
    });


    function checkAdBlock() {
        setTimeout(function (e) {
            var ad = document.querySelector("ins.adsbygoogle");
            if (ad && ad.innerHTML.replace(/\s/g, "").length == 0 && $(window).width() >= 1024) {
                alert("Please Disable AdBlock! AdBlock may cause our game not working!");
            }
        }, 10000);

        setTimeout(function (e) {
            var ad = document.querySelector("ins.adsbygoogle");
            if (ad && ad.innerHTML.replace(/\s/g, "").length == 0 && $(window).width() >= 1024) {
                alert("Please Disable AdBlock! AdBlock may cause our game not working!");
            }
        }, 120000);
    }

});