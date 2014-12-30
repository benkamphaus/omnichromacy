(ns rs-clj.io
  (:require [clojure.core.matrix :as mat]
            [nio.core :as nio])
  (:import [java.nio ByteBuffer]))

(mat/set-current-implementation :vectorz)

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

     ; this switch statement w/copy paste is not intended to be permanent
     ; but works until I get my brain in order.
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
  "Read flat binary array from file and return in shape of img cube
  specified by dims with type specified by dt (e.g., :long). No exception
  handling at present!"
  [f dims dt & {:keys [endian] :or {endian :little-endian}}]
  (let [a (mat/new-array dims)]
    (-> (slurp-binary f dt :endian endian)
        (mat/array)
        (mat/reshape dims))))

(defn raw-dump
  "Dump every byte in bstream into file, not implemented."
  [bstream f]
  nil)

(defn spit-image-cube
  "not implemented"
  [arr]
  nil)

(comment

  ; WIP notes are here.
  (require 'rs-clj.io)
  (in-ns 'rs-clj.io)

  (def nireland-hsi
    (time
    (slurp-image-cube "data/nireland.dat" [472 682 128] :short)))
  ;~2 sec
)
