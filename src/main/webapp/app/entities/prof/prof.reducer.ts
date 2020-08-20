import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IProf, defaultValue } from 'app/shared/model/prof.model';

export const ACTION_TYPES = {
  FETCH_PROF_LIST: 'prof/FETCH_PROF_LIST',
  FETCH_PROF: 'prof/FETCH_PROF',
  CREATE_PROF: 'prof/CREATE_PROF',
  UPDATE_PROF: 'prof/UPDATE_PROF',
  DELETE_PROF: 'prof/DELETE_PROF',
  RESET: 'prof/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IProf>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false,
};

export type ProfState = Readonly<typeof initialState>;

// Reducer

export default (state: ProfState = initialState, action): ProfState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_PROF_LIST):
    case REQUEST(ACTION_TYPES.FETCH_PROF):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_PROF):
    case REQUEST(ACTION_TYPES.UPDATE_PROF):
    case REQUEST(ACTION_TYPES.DELETE_PROF):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_PROF_LIST):
    case FAILURE(ACTION_TYPES.FETCH_PROF):
    case FAILURE(ACTION_TYPES.CREATE_PROF):
    case FAILURE(ACTION_TYPES.UPDATE_PROF):
    case FAILURE(ACTION_TYPES.DELETE_PROF):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_PROF_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.FETCH_PROF):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_PROF):
    case SUCCESS(ACTION_TYPES.UPDATE_PROF):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_PROF):
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

const apiUrl = 'api/profs';

// Actions

export const getEntities: ICrudGetAllAction<IProf> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_PROF_LIST,
  payload: axios.get<IProf>(`${apiUrl}?cacheBuster=${new Date().getTime()}`),
});

export const getEntity: ICrudGetAction<IProf> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_PROF,
    payload: axios.get<IProf>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IProf> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_PROF,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IProf> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_PROF,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IProf> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_PROF,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
