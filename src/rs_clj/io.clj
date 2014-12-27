(ns rs-clj.io
  (:require [clojure.core.matrix :as mat]
            [clojure.java.io :refer [input-stream]]
            [nio.core :as nio])
  (:import [java.nio ByteBuffer]))

(mat/set-current-implementation :jblas)

(defn raw-read
  "Read every byte in file to heap ByteBuffer"
  [f & {:keys [endian] :or {endian :little-endian}}]
  (let [channel (nio/channel f)
        size (.size channel)
        buf (ByteBuffer/allocate size)]
    (do (.read channel buf 0)
        (nio/set-byte-order! buf endian))
    buf))

(defn slurp-binary
  "Slurp binary file of consisting of a flat array. Accepted datatypes are:
    :byte :short :int :long :float :double
  Can specify optional :endian keyword argument as :little-endian or :big-endian"
  [f dt & {:keys [endian] :or {endian :little-endian}}]

  (let [bbuf (.flip (raw-read f :endian endian))]
     (cond (= dt :byte) (.array bbuf)
            (= dt :short) (let [buf (.asShortBuffer bbuf)
                                arr (short-array (.limit buf))]
                            (do (.get buf arr))
                            arr)
            (= dt :int) (let [buf (.asIntBuffer bbuf)
                              arr (int-array (.limit buf))]
                            (do (.get buf arr))
                            arr)
            (= dt :long) (let [buf (.asLongBuffer bbuf)
                              arr (long-array (.limit buf))]
                            (do (.get buf arr))
                            arr)
            (= dt :float) (let [buf (.asFloatBuffer bbuf)
                              arr (float-array (.limit buf))]
                            (do (.get buf arr))
                            arr)
            (= dt :double) (let [buf (.asDoubleBuffer bbuf)
                                arr (double-array (.limit buf))]
                            (do (.get buf arr))
                           arr))))

(defn slurp-image-cube
  [f dims dt & {:keys [endian] :or {endian :little-endian}}]
  (let [[x y z] dims]
    (-> (slurp-binary f dt :endian endian)
        (mat/reshape dims))))

(defn raw-dump
  "Dump every byte in bstream into file"
  [bstream f]
  nil)

(defn reshape-xduce [arr x y z]
  "Don't use this, I'm just trying to figure out why reshape is so slow."
  (transduce (comp (partition-all x)
                   (partition-all y)
                   (partition-all z))
             conj
             arr))

(comment

  ; WIP notes will go here
  (require 'rs-clj.io)
  (in-ns 'rs-clj.io)

  ; simple bsq image
  (def _ramp (reshape (range 100000) [100 100 10]))

  (def nireland-hsi
    (time
    (slurp-image-cube "data/nireland.dat" [472 682 128] :short)))
)
