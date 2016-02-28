# peer-cljs

peer.js wrapper written by cljs

## Overview

FIXME: Write a paragraph about the library/project and highlight its goals.

## Examples

### Create the Peer EDN

```clojure 
(ns peer-test.core
  (:require [peer-cljs.core :refer :all]))

(go
  (def my-peer
    (<! (peer my-api-key))))
```

### Data connections

#### Start connection

```clojure
(go
  (def my-connection
    (<! (connect my-peer another-peer-id))))
```

#### Receive connection

```clojure
(go
  (def my-connection
    (<! accept my-peer)))
```

#### Send messages

```clojure
(send! my-connection "Hello!")
```

#### Receive messages

```clojure
(go 
  (while true
    (println (<! (receive my-connection)))))
```

## Setup

To get an interactive development environment run:

    lein figwheel

and open your browser at [localhost:3449](http://localhost:3449/).
This will auto compile and send all changes to the browser without the
need to reload. After the compilation process is complete, you will
get a Browser Connected REPL. An easy way to try it is:

    (js/alert "Am I connected?")

and you should see an alert in the browser window.

To clean all compiled files:

    lein clean

To create a production build run:

    lein do clean, cljsbuild once min

And open your browser in `resources/public/index.html`. You will not
get live reloading, nor a REPL. 

## License

Copyright ¢í 2016 BOXP

Distributed under the Eclipse Public License either version 1.0 or (at your option) any later version.
