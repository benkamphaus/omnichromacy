(ns rs-clj.io
  (:require [clojure.core.matrix :refer :all]
            [clojure.java.io :refer [input-stream]]
            [nio.core :as nio]
            [bytebuffer.buff :as bb])
  (:import [org.apache.commons.io IOUtils]))

(defn raw-read
  "Read every byte in file to heap ByteBuffer"
  [f]
  (-> (input-stream f)
      (IOUtils/toByteArray)
      (nio/byte-buffer)))

(defn slurp-img-cube
  "Read an image cube from a raw binary file. At present, a horribly
  inefficient way to read a file."
  [f dims dt-str]
  (let [pix-count (reduce * dims)
        buf (raw-read f)]
    (reshape (repeatedly pix-count #(bb/take-short buf)) dims)))

(defn raw-dump
  "Dump every byte in bstream into file"
  [bstream f]
  nil)

(comment

  ; WIP notes will go here

  ; simple bsq image
  (def _ramp (reshape (range 100000) [100 100 10]))

  (def nireland-img-cube 
    (slurp-img-cube "data/nireland.dat" [472 682 128] "s"))

)
