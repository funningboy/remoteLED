
import SocketServer
import gevent
import json
import thread
import RPi.GPIO as GPIO

# LED map table
CONTEXT = {
        'LED1' : {
            'gpio'  : 16,
            'power' : 0,
            },
        'LED2' : {
            'gpio'  : 18,
            'power' : 0,
            },
        }

# GPIO setting table, pin 16, 18 as output
GPIO.setmode(GPIO.BCM)
[GPIO.setup(CONTEXT[k]['gpio'], GPIO.OUT) for k in CONTEXT.keys()]


class TCPHandler(SocketServer.BaseRequestHandler):
    """ TCP socket handler """

    #override
    def handle(self):
        global CONTEXT
        self.data = self.request.recv(1024).strip()
        print "{} wrote:".format(self.client_address[0])
	CONTEXT.update(json.loads(self.data))
        gevent.sleep(0) # switch to GPIO handler


class GPIOHandler():
    """ GPIO handler """

    def __init__(self):
        pass

    def serve_forever(self):
        global CONTEXT
        while True:
            print CONTEXT
            [GPIO.output(CONTEXT[k]['gpio'], CONTEXT[k]['power']) for k in CONTEXT.keys()]
            gevent.sleep(0) # switch to TCP handler


if __name__ == "__main__":
    HOST, PORT = '192.168.1.12', 5000

    server = SocketServer.TCPServer((HOST, PORT), TCPHandler)
    gpio   = GPIOHandler()

    gevent.joinall([
        gevent.spawn(server.serve_forever),
        gevent.spawn(gpio.serve_forever),
        ])


