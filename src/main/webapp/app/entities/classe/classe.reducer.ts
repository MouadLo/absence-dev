import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IClasse, defaultValue } from 'app/shared/model/classe.model';

export const ACTION_TYPES = {
  FETCH_CLASSE_LIST: 'classe/FETCH_CLASSE_LIST',
  FETCH_CLASSE: 'classe/FETCH_CLASSE',
  CREATE_CLASSE: 'classe/CREATE_CLASSE',
  UPDATE_CLASSE: 'classe/UPDATE_CLASSE',
  DELETE_CLASSE: 'classe/DELETE_CLASSE',
  RESET: 'classe/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IClasse>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false,
};

export type ClasseState = Readonly<typeof initialState>;

// Reducer

export default (state: ClasseState = initialState, action): ClasseState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_CLASSE_LIST):
    case REQUEST(ACTION_TYPES.FETCH_CLASSE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_CLASSE):
    case REQUEST(ACTION_TYPES.UPDATE_CLASSE):
    case REQUEST(ACTION_TYPES.DELETE_CLASSE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_CLASSE_LIST):
    case FAILURE(ACTION_TYPES.FETCH_CLASSE):
    case FAILURE(ACTION_TYPES.CREATE_CLASSE):
    case FAILURE(ACTION_TYPES.UPDATE_CLASSE):
    case FAILURE(ACTION_TYPES.DELETE_CLASSE):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_CLASSE_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.FETCH_CLASSE):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_CLASSE):
    case SUCCESS(ACTION_TYPES.UPDATE_CLASSE):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_CLASSE):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {},
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState,
      };
    default:
      return state;
  }
};

const apiUrl = 'api/classes';

// Actions

export const getEntities: ICrudGetAllAction<IClasse> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_CLASSE_LIST,
  payload: axios.get<IClasse>(`${apiUrl}?cacheBuster=${new Date().getTime()}`),
});

export const getEntity: ICrudGetAction<IClasse> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_CLASSE,
    payload: axios.get<IClasse>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IClasse> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_CLASSE,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IClasse> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_CLASSE,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IClasse> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_CLASSE,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
