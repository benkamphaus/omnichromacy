(ns omnichromacy.io
  (:require [clojure.core.matrix :as mat]
            [nio.core :as nio]
            [omnichromacy.core :refer [image-cube spectrum]]
            [clojure.string :as str])
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

(defn from-envi-file
  "Example that splits entire file format into ImageCube record type."
  [fname] nil)
  ;; get metadata we care about from envi header with parse-envi-header on slurped basename of file + .hdr
  ;; detect interleave as well (add to parse-envi-header)
  ;; get into correctly aligned spectral matrix
  ;; return ImageCube record/type from map as returned value from this function

(defn parse-envi-header
  "Return relevant fields from ENVI header file. Means right now is temporary
  until replaced w/more robust matching, i.e. regex."
  [hdr-text] 
  (let [as-vec (into [] (map #(.replaceAll % "\r" "")
                             (str/split hdr-text #"[=\n]")))
        cols (read-string (nth as-vec (inc (.indexOf as-vec "samples "))))
        rows (read-string (nth as-vec (inc (.indexOf as-vec "lines   "))))
        bands (read-string (nth as-vec (inc (.indexOf as-vec "bands   "))))
        wls (into [] (map read-string
                           (str/split (.replaceAll 
                                        (apply str 
                                          (first (rest (split-at 
                                                        (inc (inc (.indexOf as-vec "wavelength "))) as-vec)))) "}" "") #",")))]
    {:cols cols
     :rows rows
     :bands bands
     :wavelengths wls}))


(defn raw-dump
  "Dump every byte in bstream into file, not implemented."
  [bstream f]
  nil)

(defn spit-image-cube
  "not implemented"
  [arr]
  nil)

