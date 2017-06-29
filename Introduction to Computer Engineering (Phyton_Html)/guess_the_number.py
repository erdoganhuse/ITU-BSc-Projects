import bottle
from random import randint

name = ' '
rand_number = 0
guess_count = 0

global saved
saved = []

def create_rand_number():
	rand_num = randint(1,100)
	return rand_num

def htmlify(title, content):
    page = '<!DOCTYPE html>\n'
    page = page + '<html>\n'
    page = page + '  <head>\n'
    page = page + '    <title>' + title + '</title>\n'
    page = page + '    <meta charset="utf-8" />\n'
    page = page + '  </head>\n'
    page = page + '  <body>\n'
    page = page + '    ' + content + '\n'
    page = page + '  </body>\n'
    page = page + '</html>\n'
    return page

@bottle.route('/save',method='POST')
def save():
    global saved
    global rand_number

    global guess_count
    guess_count = guess_count + 1

    tosave = bottle.request.POST.get('savename')
    saved = saved + [tosave]
    num = int(tosave)

    my_string = ''
    if num > rand_number:
		my_string = my_string + '<h3>Your guess is to high</h3>'
		my_string = my_string + '<h3>Press <a href="/start">Enter</a> to guess again</h3>'
    elif num < rand_number:
		my_string = my_string + '<h3>Your guess is to low</h3>'
		my_string = my_string + '<h3>Press <a href="/start">Enter</a> to guess again</h3>'
    elif num == rand_number:
		my_string = my_string + '<h3>Congrulations !!!</h3>'
		my_string = my_string + 'Guess Count : '
		my_string = my_string + str(guess_count)
		my_string = my_string + ' Guess list : '
		cntr = 0
		while cntr < len(saved):
			my_string = my_string + ' '
			my_string = my_string + saved[cntr]
			cntr = cntr + 1
		my_string = my_string + '<h3>Press <a href="/">Main</a> to play game again</h3>'
    return htmlify('Guess Page',my_string)

@bottle.route('/start')
def start():
    return htmlify('Guess Page',
    '''
	<h2>GUESS THE NUMBER GAME</h2>
	<form action="/save" method="post">
	<h3>Enter Number : </h3>
	<input type="text" name="savename" /> <br />
	<input type="submit" value="Save" />
	</form>
    '''
    )

@bottle.route("/")
def default():
    global rand_number
    num = create_rand_number()
    rand_number = num
    return htmlify('Start page',
    '''
    <table>
	<tr><td>
    <h2>GUESS THE NUMBER GAME</h2>
    </tr></td>
    <tr><td>
    <h3>Press <a href="/start">START</a> to play the game</h3>
    </tr></td>
    </table>
    '''
    )

bottle.run(host="localhost",port="8080") 
